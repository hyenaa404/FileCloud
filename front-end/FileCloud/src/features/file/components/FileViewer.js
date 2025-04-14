import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
// import { fetchFile } from "./fileSlide";
import { fetchFile } from "../fileThunk";

const FileViewer = () => {
    const dispatch = useDispatch();
    const fileData = useSelector((state) => state.file.fileData);
    const status = useSelector((state) => state.file.status);

    useEffect(() => {
        
        dispatch(fetchFile(1));
    }, [dispatch]);

    if (status === "loading") return <p>Loading file...</p>;
    if (status === "failed") return <p>Failed to load file.</p>;

    return (
        <div>
            <h2>File Preview</h2>
            {fileData && (
                <iframe 
                    src={`data:application/pdf;base64,${fileData}`} 
                    width="100%" 
                    height="500px"
                ></iframe>
                // <iframe
                //     src={`https://docs.google.com/gview?url=http://localhost:8080/FileCloud/FileCloud/file.xlsx&embedded=true`}
                //     src={`data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,${fileData}`}
                //     width="100%"
                //     height="500px"
                // ></iframe>
            )}
        </div>
    );
};

export default FileViewer;
