package dev.alfredokang.hexagonalarchitecture.application.adapters.dtos;

public class ControllerResponse {
    public String msg;
    public String flag;
    public long dataLength;
    public Object data;

    public ControllerResponse(String msg, String flag, Object data) {
        this.msg = msg;
        this.flag = flag;
        this.data = data;
    }

    public ControllerResponse(String msg, String flag, Object data, long dataLength) {
        this.msg = msg;
        this.flag = flag;
        this.dataLength = dataLength;
        this.data = data;
    }

    public ControllerResponse(String msg, String flag) {
        this.msg = msg;
        this.flag = flag;
    }
}
