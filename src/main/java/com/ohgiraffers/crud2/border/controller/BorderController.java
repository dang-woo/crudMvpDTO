package com.ohgiraffers.crud2.border.controller;

import com.ohgiraffers.crud2.border.DTO.BorderDTO;
import com.ohgiraffers.crud2.border.service.BorderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
//컨트롤러랑 mvc 설명하기
/*
* m : model -> DTO entity repository
* v : view -> json html 타임리프
* c : controller -> controller service
*
* model
* - entity란?
*   jpa 에서 관리하는 영속성 컨텍스트로 @entity를 쓰면 jpa에서 관리하게되고 @Table 를 통해 테이블과 매핑해준다
* - DTO를 쓰는이유 엔티티를 그대로 가져다가쓰면 데이터가 변질될수 있으므로 DTO로 관리를 한다 그리고 민감한 정보의 노출을 피할수있다
*   repository - db접근 메서드  db의 연결 해제 자원관리 crud 작업처리는 다 레파지토리를 통해서한다
* view
*   사용자 인터페이스 즉 프론트엔드를 말한다 여기서는 restapi로 보내줘서 없긴한데 그나마 json?? 정도가 view에 가깝지 않을까..?
*
* controller 검증
*   사실 컨트롤러에 서비스를 넣어도 됀다 하지만 컨트롤러가 너무 많은 역할을 담당하므로 비즈니스 로직을 따로 처리하는 서비스계층을 만들어준다
*   controller - url로 들어온걸 매핑시켜주고 서비스를 호출하는 역할? 정도 =  서블렛의 역할을 해준다
*   service - 비즈니스 로직을 처리한다  ex)커피를 주문하면 -> 바리스타가 원두를 갈고 물을넣는 등 제조과정 에 속한다.
*              여기서는 음..  crud를 처리한다
* */

@RestController
@RequestMapping("/border")
public class BorderController {
    private final BorderService borderService;
    public BorderController(BorderService borderService) {
        this.borderService = borderService;
    }

    //전체조회
    @GetMapping //위에 기본경로로 들어왔을때
    public List<BorderDTO> getAllBorders() {
        List<BorderDTO> borders = borderService.getAllBorders(); //서비스에서 BorderDTO 리스트 받아옴
        return borders; // 클라이언트한테 보여준다.
    }
    //단일조회
    @GetMapping("/{id}") // /border/n번 으로 들어왔을때
    public BorderDTO getBorderById(@PathVariable Long id) { //동적이기위해 패스벨류 사용 id값이 값이된다
        Optional<BorderDTO> border = borderService.getBorderById(id); //서비스에서 받아온 값을 받아와서 border변수에 담아줌
        return border.orElse(null); //나중에 생각하기 ->  null 방지 할려했는데 왜 다시other에 null을? 아무튼 빈값이아니면 정상동작됌
    }
    //추가
    @PostMapping
    public String save(@RequestBody BorderDTO borderDTO){


        boolean result = borderService.save(borderDTO); //성공 실패여부 서비스에서 true false로 받아옴
        String message = "";
        if(result){
            message = "등록 성공"; //true면 성공
        }else {
            message = "등록실패 또는 이미있는 게시글"; //false 면 실패
        }
        return message;


    }
    //삭제
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable Long id){
//        borderService.delete(id);
//        return "삭제되었습니다";
//    }

    //삭제
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        boolean result = borderService.delete(id); // 위에처럼 성공여부 불리언으로 받아옴
        String message = "";
        if(result == true){
            message = "삭제 성공"; //트루면성공
        } else {
            message = "삭제 실패"; //펄스면 실패
        }
        return message;
    }

    //수정
    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody BorderDTO borderDTO)//유저가 입력한 id랑 컨텐츠 타이틀 받아옴
    {
        boolean result = borderService.update(id, borderDTO); // 서비스에서 true 또는 false로 성공여부 받아옴
        String message = "";
        if(result == true){
            message = "수정 성공"; // 성공하면 수정성공 알림 보내줌
        } else {
            message = "수정 실패"; // 실패하면 성공실패
        }
        return message;

    }

}
