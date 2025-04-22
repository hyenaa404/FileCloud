export const getFileIcon = (type) => {
    switch (type.toLowerCase()) {
        case "pdf":
            return "📄";
        case "doc":
        case "docx":
        case "word":
            return "📝";
        case "jpg":
        case "png":
        case "jpeg":
        case "image":
            return "🖼️";
        case "mp4":
        case "mov":
            return "🎬";
        case "xls":
        case "xlsx":
        case "excel":
            return "📊";
        case "zip":
            return "🗜️";
        default:
            return "📁";
    }
};

