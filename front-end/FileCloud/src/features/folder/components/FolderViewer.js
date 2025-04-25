import { fetchFolder } from "../folderThunk";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Container, Row, Col, Card, Button } from "react-bootstrap"
import "../../../styles/folder.css"
import { getFileIcon } from "../../../util/getFileIcon";
import { Link } from "react-router-dom";
import { useParams } from "react-router-dom";
import UploadFile from "../../file/components/UploadFile";
import { deleteFile, updateFile } from "../../file/fileThunk";
import { getFileNameWithoutExtension } from "../../../util/stringUtil";
import axiosInstance from "../../../services/axiosInstance";

export const FolderViewer = () => {
  const { folderID } = useParams();
  const dispatch = useDispatch();
  const status = useSelector((state) => state.folder.status)
  const fileList = useSelector((state) => state.folder.fileList);
  const folderList = useSelector((state) => state.folder.folderList);
  const [url, setUrl] = useState(null);

  useEffect(() => {
    const action = "delete"
    dispatch(fetchFolder(folderID))

    console.log(folderList)
    console.log(fileList)
  }, [dispatch, folderID])

  const handleDelete = (fileID) => {

    dispatch(deleteFile({ fileID }))
      .unwrap()
      .then(() => {
        dispatch(fetchFolder(folderID))
      })

  }

  const handleRename = (fileID, newName) => {

    dispatch(updateFile({ fileID, newName }))
      .unwrap()
      .then(() => {
        dispatch(fetchFolder(folderID))
      })

  }

  // const handleDownload = (fileID) => {
  //   const fetchBlob = async () => {
  //     const response = await axiosInstance.get('/file?FileID=' + fileID + "&action=download", {
  //         responseType: 'blob'
  //     });
  //     const url = URL.createObjectURL(response.data);
  //     setUrl(url);
  // };
  // fetchBlob();
  // }

  const handleDownload = (fileID, fileName) => {
    const fetchBlob = async () => {
      const response = await axiosInstance.get('/file?FileID=' + fileID + "&action=download", {
        responseType: 'blob'
      });
      
      const url = URL.createObjectURL(response.data);
  
      
      const link = document.createElement('a');
      link.href = url;
      
      link.download = fileName; 
      
      document.body.appendChild(link);
      link.click();
  
      document.body.removeChild(link);
  
      URL.revokeObjectURL(url);
    };
  
    fetchBlob();
  };
  

  const [openMenuId, setOpenMenuId] = useState(null);
  

  // const toggleMenu = () => setShowMenu(!showMenu);




  if (status === "succeeded") {

    const folders = (folderList ?? []).map(folder => {

      return (<>
        <Col
          key={folder.folderID}
          lg={2}
          md={3}
          sm={4}
          xs={6}
          className="folder-col"
        >
          <Link to={`/folder/${folder.folderID}`} className="folder-link">
            <div className="folder-card">
              <div className="folder-icon">📁</div>
              <div className="folder-name">{folder.name}</div>
              <div className="folder-meta">By John Doe</div>
              <div className="folder-meta">Uploaded: {folder.createdAt}</div>
            </div>
          </Link>
        </Col>
      </>)

    })



    const files = (fileList ?? []).map(file => {
      const isMenuOpen = openMenuId === file.fileID;
    
      const toggleMenu = () => {
        setOpenMenuId(prev => (prev === file.fileID ? null : file.fileID));
      };
    
      const confirmDeleteFile = () => {
        if (window.confirm("Are you sure you want to delete this file?")) {
          handleDelete(file.fileID);
        }
        setOpenMenuId(null);
      };
    
      const handleRenameFileClick = () => {
        const newName = window.prompt("Enter new name for the file:", getFileNameWithoutExtension(file.name));
        if (newName) {
          handleRename(file.fileID, newName);
          // console.log("Renamed to:", newName);
        }
        setOpenMenuId(null);
      };
    
      return (
        <Col
          key={file.fileID}
          lg={2}
          md={3}
          sm={4}
          xs={6}
          className="file-col"
        >
          <Link to={`/file/${file.fileID}`} className="folder-link">
            <div className="file-card">
              <div className="file-icon">{getFileIcon(file.fileType)}</div>
              <div className="file-name">{file.name}</div>
              <div className="file-meta">By John Doe</div>
              <div className="file-meta">Uploaded: {file.uploadedAt}</div>
              <div className="file-meta">Type: {file.fileType}</div>
            </div>
          </Link>
          <div className="file-options">
            <button className="more-btn" onClick={toggleMenu}>⋯</button>
            {isMenuOpen && (
              <div className="options-menu">
                <button onClick={handleRenameFileClick}>Rename</button>
                <button onClick={confirmDeleteFile}>Delete</button>
                <button onClick={()=>handleDownload(file.fileID, file.name)}>Download</button>
              </div>
            )}
          </div>
        </Col>
      );
    });
    

    
    return <Container><Row>{folders}</Row><Row>{files}</Row><UploadFile /></Container>
  } else if (status === "loading") {
    return <p>loading</p>
  } else return <p>failed</p>
}

