package com.ohgiraffers.crud2.border.service;

import com.ohgiraffers.crud2.border.DTO.BorderDTO;
import com.ohgiraffers.crud2.border.entity.Border;
import com.ohgiraffers.crud2.border.repository.BorderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BorderService {
    private final BorderRepository borderRepository;

    public BorderService(BorderRepository borderRepository) {
        this.borderRepository = borderRepository;
    }

    /*---전체조회-------------------------------------------------------------------------------------*/
    public List<BorderDTO> getAllBorders() {
        List<Border> borders = borderRepository.findAll();// DB에서 모든 Border 엔티티 조회
        //여기서부터는 엔티티를 DTO로 변환하는 과정이다.
        List<BorderDTO> borderDTOs = new ArrayList<>(); //Border 엔티티를 BorderDTO로 변환해 저장할 리스트 생성
        for (Border border : borders) { // 각 Border 엔티티를 BorderDTO로 변환
            BorderDTO borderDTO = new BorderDTO(); // 새로운 DTO객체를 만들어주고
            borderDTO.setTitle(border.getTitle()); // 엔티티의 title와 content를 DTO에 매핑해준다
            borderDTO.setContent(border.getContent());
            borderDTOs.add(borderDTO); //그리고 변환된 DTO를 리스트에 추가해준다
        }
        return borderDTOs; //DTO리스트 반환
    }
    // 엔티티버전
    //    public List<Board> findallboards() {
    //        List<Board> boards = boardRepository.findAll();
    //        return boards;
    //    }


    /* ---단일조회------------------------------------------------------------------------------------- */
    //Optional 을 사용하는 이유 : null값 방지 하기위해 -> null방지를 안하면 null이 리턴될때 에러가 날 수 있다.
    //Optional<border> -> 옵셔널으로 감싸져있는 border에서 border을 가져오려면  border.get() 를사용 ->  border 보더가 가져와짐.
    // .of 입력값이 있다고 가정할때 사용 만약 .of를 사용했는데 null이 나오면 익셉션을 던진다고한다.
    // null가능성이 있다면 .ofNullable을 대신 사용하는것이 좋다고한다
    // 하지만 여기서는 이미 title와 content가 있다는 가정이니 .of 를 사용하자
    public Optional<BorderDTO> getBorderById(Long id) {
        Optional<Border> border = borderRepository.findById(id); //DB에서 id를 통해 Border 엔티티 조회
        //엔티티를 DTO로 변환하는 과정
        if (border.isPresent())  //.isPresent <- 값이 있으면 true를 반환하고 없으면 false를 반환
        {
            BorderDTO borderDTO = new BorderDTO(); //새로운 DTO객체 만들어줌
            borderDTO.setTitle(border.get().getTitle()); //엔티티의 title와 content를 DTO에 매핑 .get()는 상단에 설명
            borderDTO.setContent(border.get().getContent());
            return Optional.of(borderDTO); //DTO 리스트 반환
        } else { // 값이 false 라면
            return Optional.empty(); //.empty() -> null 대신 안전하게 빈 상태를 나타낼 때 사용
        }
    }
    //    엔티티버전
    //    public Optional<Border> getBorderById(Long id) {
    //        return borderRepository.findById(id);
    //    }

    /* ---추가------------------------------------------------------------------------------------- */
    //추가

    // 같은제목 등록금지 해보기 (미완성)
    public boolean save(BorderDTO borderDTO){
        //중복체크
        if (borderRepository.findByTitle(borderDTO.getTitle()).isPresent())
        {
            return false;//타이틀을 찾았을때 있으면 false반환
        }
        Border border = new Border();  //위와 같음
        border.setTitle(borderDTO.getTitle()); //위와 같음
        border.setContent(borderDTO.getContent());

        borderRepository.save(border);

        if(border != null){
            return true;
        }else {
            return false;
        }

    }



    /* ---삭제------------------------------------------------------------------------------------- */
    //삭제
//    public void delete(Long id){
//        borderRepository.deleteById(id);
//    }

    public boolean delete(Long id) {
        if (borderRepository.existsById(id)) // .existsById DB에서 id 존재하는지확인
        {
            borderRepository.deleteById(id); //있으면 삭제
            return true; // true 반환
        }
        return false; //없으면 false반환
    }
    /* ---수정------------------------------------------------------------------------------------- */
    //수정
    public boolean update(Long id, BorderDTO borderDTO){
        if (borderRepository.existsById(id)) //db에서 아이디 있는지 확인
        {
            borderDTO.setId(id);
            //border 객체를 새로 만들어서 borderDTO에서 가져온 id title content를 사용 없으면 추가 있으면 업데이트함
            borderRepository.save(new Border(borderDTO.getId(), borderDTO.getTitle(), borderDTO.getContent()));
            return true; //수정 완료되면 true반환
        }
        return false; // 수정실패하면 false반환
    }



}
