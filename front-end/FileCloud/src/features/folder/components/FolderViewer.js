import { fetchFolder } from "../folderThunk";
import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Container, Row, Col, Card } from "react-bootstrap"

export const FolderViewer = () => {
    const dispatch = useDispatch();
    const status = useSelector((state)=> state.folder.status)
    const fileList = useSelector((state)=> state.folder.fileList);
    const folderList = useSelector((state)=> state.folder.folderList);

    useEffect(()=> {
        dispatch(fetchFolder())

        console.log(folderList)
        console.log(fileList)
    }, [dispatch])

    if(status === "succeeded"){
    const folders = folderList.map(folder=> {
        return(<>
        <Col lg={2} md={3} sm={3} xs={4} key={folder.folderID}>
                            <Card style={{margin: '10px'}}>
                            
                                <Card.Body style={{fontSize: '10px'}}>
                                    <Card.Title style={{fontSize: '15px'}}>{folder.name}</Card.Title>
                                    <Card.Text>
                                        John Doe{/* {folder.createdAt} */}
                                    </Card.Text>
                                    <Card.Text>Uploaded: {folder.createdAt} </Card.Text>
                                    
                                </Card.Body>
                            </Card>
                        </Col>
        </>)
    })
    const files = fileList.map(file=> {
        return(
            <><Col lg={2} md={3} sm={3} xs={4} key={file.fileID}>
            <Card style={{margin: '10px'}}>
            
                <Card.Body style={{fontSize: '10px'}}>
                    <Card.Title style={{fontSize: '15px'}}>{file.name}</Card.Title>
                    <Card.Text>
                        John Doe{/* {folder.createdAt} */}
                    </Card.Text>
                    <Card.Text>Uploaded: {file.uploadedAt} </Card.Text>
                    <Card.Text>Type: {file.fileType} </Card.Text>
                    
                </Card.Body>
            </Card>
        </Col></>
        )
    })
    return <Container><Row>{folders}</Row><Row>{files}</Row></Container>
}else if (status === "loading"){
    return <p>loading</p>
}else return <p>failed</p>
}
    
