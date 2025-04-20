export const getFileIcon = (type) => {
    switch (type.toLowerCase()) {
      case "pdf":
        return "📄";
      case "doc":
      case "docx":
        return "📝";
      case "jpg":
      case "png":
      case "jpeg":
        return "🖼️";
      case "mp4":
      case "mov":
        return "🎬";
      case "zip":
        return "🗜️";
      default:
        return "📁";
    }
  };