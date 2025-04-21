import { fetchFolder } from "../folderThunk";
import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Container, Row, Col, Card } from "react-bootstrap"
import "../../../styles/folder.css"
import { getFileIcon } from "../../../util/getFileIcon";
import { Link } from "react-router-dom";
import { useParams } from "react-router-dom";
import UploadFile from "../../file/components/UploadFile";

export const FolderViewer = () => {
    const { folderID } = useParams();
    const dispatch = useDispatch();
    const status = useSelector((state)=> state.folder.status)
    const fileList = useSelector((state)=> state.folder.fileList);
    const folderList = useSelector((state)=> state.folder.folderList);

    useEffect(()=> {
        dispatch(fetchFolder(folderID))

        console.log(folderList)
        console.log(fileList)
    }, [dispatch, folderID])

    if(status === "succeeded"){
        
    const folders = (folderList ?? []).map(folder=> {
        
        return(<>
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
   
    const files = (fileList ?? []).map(file=> {
        
        return(
            <><Col
            key={file.fileID}
            lg={2}
            md={3}
            sm={4}
            xs={6}
            className="file-col"
          >
            <div className="file-card">
              <div className="file-icon">{getFileIcon(file.fileType)}</div>
              <div className="file-name">{file.name}</div>
              <div className="file-meta">By John Doe</div>
              <div className="file-meta">Uploaded: {file.uploadedAt}</div>
              <div className="file-meta">Type: {file.fileType}</div>
            </div>
          </Col></>
        )
    })
    return <Container><Row>{folders}</Row><Row>{files}</Row><UploadFile/></Container>
}else if (status === "loading"){
    return <p>loading</p>
}else return <p>failed</p>
}
    
