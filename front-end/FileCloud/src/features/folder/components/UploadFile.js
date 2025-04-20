import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { uploadFile, fetchFolder } from "../folderThunk";
import { useParams } from "react-router-dom";

const UploadFile = () => {
  const { folderId } = useParams(); 
  const [file, setFile] = useState(null);
  const dispatch = useDispatch();
  const uploadStatus = useSelector((state) => state.folder.uploadStatus);

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleUpload = () => {
    if (!file) return;
    dispatch(uploadFile({ folderId, file }))
      .unwrap()
      .then(() => {
        dispatch(fetchFolder(folderId)); // Refresh folder content sau khi upload
      });
  };

  return (
    <div className="upload-container">
      <h3>Upload File</h3>
      <div className="upload-input">
        <input type="file" onChange={handleFileChange} />
      </div>
      <button className="upload-button" onClick={handleUpload}>
        Upload
      </button>
      <p className="status-text">Status: {uploadStatus}</p>
    </div>
  );
};

export default UploadFile;
