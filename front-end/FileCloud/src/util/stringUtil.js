export const getFileNameWithoutExtension = (fileName) => {
    var  dotIndex = fileName.lastIndexOf('.');
    return (dotIndex != -1) ? fileName.substring(0,dotIndex ).toLowerCase() : "";
}