import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { uploadFile } from "../fileThunk";
import { useParams } from "react-router-dom";
import { fetchFolder } from "../../folder/folderThunk";
import { Button } from "react-bootstrap";

const UploadFile = () => {
  const { folderID } = useParams();
  const [file, setFile] = useState(null);
  const dispatch = useDispatch();
  const uploadStatus = useSelector((state) => state.folder.status);

  const [isVisible, setIsVisible] = useState(false);

  const handleClick = () => {
    setIsVisible(!isVisible);
  };

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleUpload = () => {
    if (!file) return;
      // const fileName = file.name;
      // const extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
      
    dispatch(uploadFile({ folderID, file }))
      .unwrap()
      .then(() => {
        dispatch(fetchFolder(folderID));
      });
  };

  return (
    <div className="upload-container">
      <Button className="btn-secondary" onClick={handleClick}>Upload</Button>
      {isVisible &&
        <div className="upload-body">
          <div className="upload-input">
            <input type="file" onChange={handleFileChange} />
          </div>
          <button className="upload-button" onClick={handleUpload}>
            Upload
          </button>
          <p className="status-text">Status: {uploadStatus}</p>
        </div>
      }
    </div>

  );
};

export default UploadFile;
