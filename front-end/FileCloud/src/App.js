import React from "react";
import { Provider } from "react-redux";
import store from "./component/file/store";
import FileViewer from "./component/file/FileViewer";

function App() {
    return (
        <Provider store={store}>
            <div className="App">
                <h1>File Viewer</h1>
                <FileViewer />
            </div>
        </Provider>
    );
}

export default App;
