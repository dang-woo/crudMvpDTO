package com.ohgiraffers.crud2.border.DTO;
/*
* DTO 를 사용하는이유
* 1.View레이어와 DB Layer 의 역할을 분리하기 위해
* 2.Entity 객체의 변경을 피하기 위해 -> 엔티티 객체를 그대로 사용하면 의도와 다르게 데이터 변질가능성이 생김
* 3.View와 통신하는 DTO클래스는 자주 변경된다
* 4.도메인 모델링을 지키기 위해
* */
public class BorderDTO {
    private Long id;
    private String title;
    private String content;

    public BorderDTO() {
    }

    public BorderDTO(String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BorderDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}