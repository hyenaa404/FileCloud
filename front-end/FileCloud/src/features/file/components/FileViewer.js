import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import { fetchFile } from "../fileThunk";
import axiosInstance from "../../../services/axiosInstance";
import { fetchFileAPI } from "../fileAPI";


const FileViewer = () => {
    const { fileID } = useParams();
    const dispatch = useDispatch();
    const status = useSelector((state) => state.file.status);
    const [url, setUrl] = useState(null); 


    useEffect(() => {
        const fetchBlob = async () => {
            const response = await axiosInstance.get('/file?FileID=' + fileID, {
                responseType: 'blob'
            });
            // const response = fetchFileAPI(fileID);
            const url = URL.createObjectURL(response.data);
            setUrl(url);
        };
    
        fetchBlob();
    }, [fileID]);

    // useEffect(() => {
        
    // }, [fileData]);

    if (status === "loading") return <p>Loading file...</p>;
    if (status === "failed") return <p>Failed to load file.</p>;

    return (
        <div>
            {url && (
                <iframe 
                    src={url}
                    width="100%" 
                    height="500px"
                    title="File Preview"
                ></iframe>
            )}
        </div>
    );
};


export default FileViewer;
