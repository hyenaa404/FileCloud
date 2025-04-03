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

