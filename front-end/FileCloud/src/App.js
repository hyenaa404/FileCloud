import React from "react";
import { Provider } from "react-redux";
import store from "./store/store";
import FileViewer from "./features/file/components/FileViewer";
import AppRouter from "./router/index"

function App() {
    return (
        <Provider store={store}>
            <div className="App">
             <AppRouter/>
                 { <FileViewer /> }
            </div>
        </Provider>
    );
}

export default App;
