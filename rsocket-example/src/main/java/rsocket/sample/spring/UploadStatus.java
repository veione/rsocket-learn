package rsocket.sample.spring;

/**
 * @author veione
 * @version 1.0.0
 * @date 2023年03月02日 10:24:00
 */
public enum UploadStatus {
    CHUNKED_COMPLETE, //文件上传处理中
    COMPLETED, //文件上传完毕
    FAILED;//失败
}
