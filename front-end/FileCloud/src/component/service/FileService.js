import axios from 'axios';

const API_URL = 'http://localhost:8080/FileCloud/file';

export const fetchFile = async () => {
  const response = await axios.get(API_URL);
  const data = await response.json();
    return data.fileData;
};
