package cn.edu.qlu.studentteachermanager.message;

import java.util.List;

/**
 * 服务器Response给客户端的消息格式
 */
public class ResultMessage {
    private Integer status;
    private List<? extends Object> rows;
    private Long total;
    private String hint;

    public ResultMessage(Integer status, List<? extends Object> rows, Long total, String hint) {
        this.status = status;
        this.rows = rows;
        this.total = total;
        this.hint = hint;
    }

    @Override
    public String toString() {
        return "ResultMessage{" +
                "status=" + status +
                ", rows=" + rows +
                ", total=" + total +
                ", hint='" + hint + '\'' +
                '}';
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<? extends Object> getRows() {
        return rows;
    }

    public void setRows(List<? extends Object> rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
