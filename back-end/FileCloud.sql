create database FileCloud;
go
use FileCloud
go
CREATE TABLE Users (
    UserID INT IDENTITY(1,1) PRIMARY KEY,
    FullName NVARCHAR(255) NOT NULL,
    Email NVARCHAR(255) UNIQUE NOT NULL,
    PasswordHash NVARCHAR(255) NOT NULL,
    CreatedAt DATETIME DEFAULT GETDATE()
);
----------update users table-----
ALTER TABLE Users
ADD IsVerified BIT DEFAULT 0
--update allow null password for google login case--
ALTER TABLE Users
ALTER COLUMN PasswordHash NVARCHAR(255) NULL;

-------------------

CREATE TABLE Folders (
    FolderID INT PRIMARY KEY IDENTITY,
    Name NVARCHAR(255) NOT NULL,
    ParentID INT, 
    OwnerID INT FOREIGN KEY REFERENCES Users(UserID),
    CreatedAt DATETIME DEFAULT GETDATE(),
    PrivacyLevel NVARCHAR(20) CHECK (PrivacyLevel IN ('Private', 'Public', 'Organization'))
);


CREATE TABLE Files (
    FileID INT PRIMARY KEY IDENTITY,
    Name NVARCHAR(255) NOT NULL,
    FolderID INT , 
    FileType NVARCHAR(50) CHECK (FileType IN ('PDF', 'Word', 'Excel', 'Image', 'Other')) NOT NULL,
    OwnerID INT FOREIGN KEY REFERENCES Users(UserID),
    FilePath NVARCHAR(500) NOT NULL,
    UploadedAt DATETIME DEFAULT GETDATE(),
    PrivacyLevel NVARCHAR(20) CHECK (PrivacyLevel IN ('Private', 'Public', 'Organization'))
);

CREATE TABLE Permissions (
    PermissionID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT NOT NULL,
    FolderID INT NULL,
    FileID INT NULL,
    Role NVARCHAR(50) CHECK (Role IN ('Owner', 'Editor', 'Viewer')) NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (FolderID) REFERENCES Folders(FolderID) ON DELETE CASCADE,
    FOREIGN KEY (FileID) REFERENCES Files(FileID) ON DELETE CASCADE,
    CONSTRAINT CHK_OnlyOneTarget CHECK (
        (FolderID IS NOT NULL AND FileID IS NULL) OR 
        (FolderID IS NULL AND FileID IS NOT NULL)
    )
);


/*============EDIT PERMISSION TABLE===================*/
DROP TABLE IF EXISTS Permissions;

CREATE TABLE Permissions (
    PermissionID INT IDENTITY(1,1) PRIMARY KEY,
    Email INT NULL,  -- allow null
    FolderID INT NULL,
    FileID INT NULL,
    Role NVARCHAR(50) CHECK (Role IN ('Owner', 'Editor', 'Viewer')) NOT NULL,
    FOREIGN KEY (Email) REFERENCES Users(Email),
    FOREIGN KEY (FolderID) REFERENCES Folders(FolderID) ON DELETE CASCADE,
    FOREIGN KEY (FileID) REFERENCES Files(FileID) ON DELETE CASCADE,
    CONSTRAINT CHK_OnlyOneTarget CHECK (
        (FolderID IS NOT NULL AND FileID IS NULL) OR 
        (FolderID IS NULL AND FileID IS NOT NULL)
    )
);
------------------------<Create trigger on Permission constraint userID base on privacy level of file/folder>--------------------------------------
--drop trigger trg_CheckPrivacyOnInsert

DROP TRIGGER IF EXISTS trg_CheckPrivacyOnInsert;
GO

CREATE TRIGGER trg_CheckPrivacyOnInsert
ON Permissions
INSTEAD OF INSERT
AS
BEGIN
    SET NOCOUNT ON;

    -------------------------
    -- CASE: File Permissions
    -------------------------
    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN Files f ON i.FileID = f.FileID
        WHERE
            (
                -- Owner case: UserID must match File.OwnerID
                i.Role = 'Owner' AND (i.UserID IS NULL OR i.UserID <> f.OwnerID)
            )
            OR
            (
                -- Non-owner: must be NULL if not Private, must NOT NULL if Private
                i.Role <> 'Owner' AND (
                    (f.PrivacyLevel = 'Private' AND i.UserID IS NULL) OR
                    (f.PrivacyLevel <> 'Private' AND i.UserID IS NOT NULL)
                )
            )
    )
    BEGIN
        RAISERROR('Invalid UserID or Role for File based on privacy level and ownership.', 16, 1);
        RETURN;
    END

    ---------------------------
    -- CASE: Folder Permissions
    ---------------------------
    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN Folders f ON i.FolderID = f.FolderID
        WHERE
            (
                -- Owner case: UserID must match Folder.OwnerID
                i.Role = 'Owner' AND (i.UserID IS NULL OR i.UserID <> f.OwnerID)
            )
            OR
            (
                -- Non-owner: must be NULL if not Private, must NOT NULL if Private
                i.Role <> 'Owner' AND (
                    (f.PrivacyLevel = 'Private' AND i.UserID IS NULL) OR
                    (f.PrivacyLevel <> 'Private' AND i.UserID IS NOT NULL)
                )
            )
    )
    BEGIN
        RAISERROR('Invalid UserID or Role for Folder based on privacy level and ownership.', 16, 1);
        RETURN;
    END

    -------------------
    -- VALID: Do insert
    -------------------
    INSERT INTO Permissions (UserID, FolderID, FileID, Role)
    SELECT UserID, FolderID, FileID, Role
    FROM inserted;
END;


--=================================================================

CREATE TABLE FileReviews (
    ReviewID INT IDENTITY(1,1) PRIMARY KEY,
    FileID INT FOREIGN KEY REFERENCES Files(FileID) ON DELETE CASCADE,
    ReviewedBy INT FOREIGN KEY REFERENCES Users(UserID) ON DELETE SET NULL,
    ReviewComment NVARCHAR(MAX),
    ReviewDate DATETIME DEFAULT GETDATE(),
    Status NVARCHAR(50) CHECK (Status IN ('Pending', 'Approved', 'Needs Changes')) NOT NULL
);

CREATE TABLE FileNotes (
    NoteID INT IDENTITY(1,1) PRIMARY KEY,
    FileID INT FOREIGN KEY REFERENCES Files(FileID) ON DELETE CASCADE,
    NoteText NVARCHAR(MAX) NOT NULL,
    CreatedBy INT FOREIGN KEY REFERENCES Users(UserID) ON DELETE SET NULL,
    CreatedAt DATETIME DEFAULT GETDATE()
);


/*========================================================*/
CREATE TRIGGER trg_InsertFolderPermission
ON Folders
AFTER INSERT
AS
BEGIN
    INSERT INTO Permissions (UserID, FolderID, FileID, Role)
    SELECT OwnerID, FolderID, NULL, 'Owner'
    FROM INSERTED;
END;

CREATE TRIGGER trg_InsertFilePermission
ON Files
AFTER INSERT
AS
BEGIN
     INSERT INTO Permissions (UserID, FolderID, FileID, Role)
	 SELECT OwnerID, NULL, FileID, 'Owner'
	 FROM INSERTED;
END;

--use FileCloud

----------UPDATE TRIGGER AFTER CHANGE IN PERMISSIONS TABLE-----------
drop trigger if exists trg_InsertFolderPermission
drop trigger if exists trg_InsertFilePermission
--trigger on folder table---
CREATE TRIGGER trg_InsertFolderPermission
ON Folders
AFTER INSERT
AS
BEGIN
    INSERT INTO Permissions (Email, FolderID, FileID, Role)
    SELECT u.Email, i.FolderID, NULL, 'Owner'
    FROM INSERTED i INNER JOIN USERS u ON i.OwnerID = u.UserID;
END;

--trigger on file table--
CREATE TRIGGER trg_InsertFilePermission
ON Files
AFTER INSERT
AS
BEGIN
     INSERT INTO Permissions (Email, FolderID, FileID, Role)
	 SELECT u.Email, NULL, i.FileID, 'Owner'
	 FROM INSERTED i INNER JOIN USERS u ON i.OwnerID = u.UserID;
END;



/*=======================================================*/

INSERT INTO Files (Name, FolderID, FileType, OwnerID, FilePath, PrivacyLevel)
VALUES 
    ('hanhnhan.pdf', NULL, 'PDF', 1, '.\user_1\hanhnhan.pdf', 'Public'),
    ('standard.xlsx', 3, 'Excel', 1, '.\user_1\1\standard.xlsx', 'Private'),
    ('photo1.jpg', 4, 'Image', 1, '.\user_1\2\photo1.jpg', 'Organization');

	INSERT INTO Files (Name, FolderID, FileType, OwnerID, FilePath, PrivacyLevel) VALUES ('test', 1, 'PDF', 1, 'a/a/a', 'private')

	select * from users
	select * from folders
	SELECT * FROM Files WHERE FolderID IS NULL

	SELECT * FROM Files WHERE FileID = 1
	
	select * from files
	select * from permissions

INSERT INTO Users (FullName, Email, PasswordHash)  
VALUES ('John Doe', 'johndoe@example.com', 'hash123');

INSERT INTO Folders (Name, ParentID, OwnerID, PrivacyLevel)  
VALUES 
    ('1', NULL, 1, 'Public'),
    ('2', NULL, 1, 'Private');

	delete from folders

	UPDATE Files
SET FilePath = 'uploads\user_1\hanhnhan.pdf'
WHERE Name = 'hanhnhan.pdf';

UPDATE Files
SET FilePath = 'uploads\user_1\1\standard.xlsx'
WHERE Name = 'standard.xlsx';

UPDATE Files
SET FilePath = 'uploads\user_1\2\photo1.jpg'
WHERE Name = 'photo1.jpg';


insert into Permissions (email, folderID, fileID, role) values 
('johndoe@example.com',null,1,'Owner'),
('johndoe@example.com',null,2,'Owner'),
('johndoe@example.com',null,3,'Owner'),
('johndoe@example.com',3,null,'Owner'),
('johndoe@example.com',4,null,'Owner')

delete from permissions

select * from permissions

use FileCloud


SELECT * FROM Folders WHERE ParentID IS NULL AND OwnerID = 1

SELECT * FROM Files WHERE FolderID = 3




DROP TABLE IF EXISTS Permissions;
GO

CREATE TABLE Permissions (
    PermissionID INT IDENTITY(1,1) PRIMARY KEY,
    Email NVARCHAR(255) NULL,
    FolderID INT NULL,
    FileID INT NULL,
    Role NVARCHAR(50) CHECK (Role IN ('Owner', 'Editor', 'Viewer')) NOT NULL,
    
    FOREIGN KEY (FolderID) REFERENCES Folders(FolderID) ON DELETE CASCADE,
    FOREIGN KEY (FileID) REFERENCES Files(FileID) ON DELETE CASCADE,

    CONSTRAINT CHK_OnlyOneTarget CHECK (
        (FolderID IS NOT NULL AND FileID IS NULL) OR 
        (FolderID IS NULL AND FileID IS NOT NULL)
    ),
    
    CONSTRAINT UQ_Email_File UNIQUE (Email, FileID),
    CONSTRAINT UQ_Email_Folder UNIQUE (Email, FolderID)
);

--FIX constraint on permission table----
ALTER TABLE PERMISSIONS DROP CONSTRAINT UQ_Email_File
ALTER TABLE PERMISSIONS DROP CONSTRAINT UQ_Email_Folder
---
GO
CREATE UNIQUE INDEX UQ_Email_File
ON Permissions(Email, FileID)
WHERE FileID IS NOT NULL;
GO
CREATE UNIQUE INDEX UQ_Email_Folder
ON Permissions(Email, FolderID)
WHERE FolderID IS NOT NULL;


--------------------------

DROP TRIGGER IF EXISTS trg_CheckPrivacyOnInsert;
GO

CREATE TRIGGER trg_CheckPrivacyOnInsert
ON Permissions
INSTEAD OF INSERT
AS
BEGIN
    SET NOCOUNT ON;

    ---------------------
    -- CASE: File Permissions
    ---------------------
    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN Files f ON i.FileID = f.FileID
        LEFT JOIN Users u ON f.OwnerID = u.UserID
        WHERE
            (
                -- Owner case: email must match email of file owner
                i.Role = 'Owner' AND (i.Email IS NULL OR i.Email <> u.Email)
            )
            OR
            (
                -- Non-owner case
                i.Role <> 'Owner' AND (
                    (f.PrivacyLevel = 'Private' AND i.Email IS NULL) OR
                    (f.PrivacyLevel <> 'Private' AND i.Email IS NOT NULL)
                )
            )
    )
    BEGIN
        RAISERROR('Invalid Email or Role for File based on privacy level and ownership.', 16, 1);
        RETURN;
    END

    ------------------------
    -- CASE: Folder Permissions
    ------------------------
    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN Folders f ON i.FolderID = f.FolderID
        LEFT JOIN Users u ON f.OwnerID = u.UserID
        WHERE
            (
                -- Owner case: email must match email of folder owner
                i.Role = 'Owner' AND (i.Email IS NULL OR i.Email <> u.Email)
            )
            OR
            (
                -- Non-owner case
                i.Role <> 'Owner' AND (
                    (f.PrivacyLevel = 'Private' AND i.Email IS NULL) OR
                    (f.PrivacyLevel <> 'Private' AND i.Email IS NOT NULL)
                )
            )
    )
    BEGIN
        RAISERROR('Invalid Email or Role for Folder based on privacy level and ownership.', 16, 1);
        RETURN;
    END

    -------------------
    -- VALID: Do insert
    -------------------
    INSERT INTO Permissions (Email, FolderID, FileID, Role)
    SELECT Email, FolderID, FileID, Role
    FROM inserted;
END;
