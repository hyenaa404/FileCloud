import React from 'react';
import { RootFolderViewer } from '../features/folder/components/RootFolderViewer';
import { Container, Row } from "react-bootstrap";
import "../styles/home.css"

const Home = () => {
  return (
    <div className="home-wrapper">
      <Container>
        <div className="home-header">
          <h1>Recent Files</h1>
          <p className="sub-title">Access your latest uploaded folders and files</p>
        </div>

        <div className="home-content">
          <Row>
            <RootFolderViewer />
          </Row>
        </div>
      </Container>
    </div>
  )
};

export default Home;