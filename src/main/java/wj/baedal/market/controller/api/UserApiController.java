package wj.baedal.market.controller.api;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import wj.baedal.market.controller.dto.user.UserListResponseDto;
import wj.baedal.market.controller.dto.user.UserResponseDto;
import wj.baedal.market.controller.dto.user.UserSaveRequestDto;
import wj.baedal.market.controller.dto.user.UserUpdateRequestDto;
import wj.baedal.market.repository.user.UserSearchCondition;
import wj.baedal.market.service.userservice.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    /**
     *  유저 정보 등록
     *
     *  Request
     *  {
     *      "name" : String,
     *      "city" : String,
     *      "street" : String,
     *      "zipcode" : String
     *  }
     *
     *  Response
     *  Long ( User's Id)
     * */
    @PostMapping("/api/v1/users")
    public Long save(@RequestBody UserSaveRequestDto userSaveRequestDto){
        return userService.save(userSaveRequestDto);
    }

    /**
     * 유저 정보 조회
     *
     * Request
     * none
     *
     * Response
     * {
     *     "count" : int,
     *     "data" : [
     *          {
     *             "id" : Long,
     *             "name" : String,
     *             "city" : String,
     *             "street" : String,
     *             "zipcode" : String
     *          },
     *          ...
     *     ]
     * }
     *
     * */

    @GetMapping("/api/v1/users")
    public UserListResponseDto findAll(){
        return userService.findAll();
    }



    /**
     *  유저 정보를 검색하고 페이징 합니다.
     *
     * Request
     * {
     *     "name" : String,
     *     "city" : String
     * }
     *
     * Response 예제
     * {
     *     "content": [
     *         {
     *             "id": 91,
     *             "name": "user90",
     *             "city": "a90",
     *             "street": "b90",
     *             "zipcode": "c90"
     *         },
     *         ...
     *     ],
     *     "pageable": {
     *         "sort": {
     *             "sorted": true,
     *             "unsorted": false,
     *             "empty": false
     *         },
     *         "offset": 90,
     *         "pageNumber": 9,
     *         "pageSize": 10,
     *         "paged": true,
     *         "unpaged": false
     *     },
     *     "totalPages": 10,
     *     "totalElements": 100,
     *     "last": true,
     *     "numberOfElements": 10,
     *     "first": false,
     *     "number": 9,
     *     "sort": {
     *         "sorted": true,
     *         "unsorted": false,
     *         "empty": false
     *     },
     *     "size": 10,
     *     "empty": false
     * }
     * */

    @PostMapping("/api/v2/users")
    public Page<UserResponseDto> searchUser(@PageableDefault(sort = {"name"},value = 10) Pageable pageable,@RequestBody UserSearchCondition condition){
        return userService.searchUser( pageable,condition);
    }


    /**
     * 유저를 아이디로 조회
     *
     * Request
     * none
     *
     * Response
     * {
     *     "id" : Long,
     *     "name" : String,
     *     "city" : String,
     *     "street" : String,
     *     "zipcode" : String
     * }
     *
     * */
    @GetMapping("/api/v1/users/{id}")
    public UserResponseDto findById(@PathVariable Long id){
        return userService.findById(id);
    }

    /**
     *  유저 주소 정보 수정
     *  Request
     *  {
     *      "city" : String,
     *      "street" : String,
     *      "zipcode" : String
     *  }
     *
     *  Response
     *  Long ( User's Id )
     * */
    @PutMapping("/api/v1/users/{id}")
    public Long updateAddress(@PathVariable Long id,
                              @RequestBody UserUpdateRequestDto requestDto){

        return userService.updateAddress(id,requestDto);
    }

    /**
     *  유저 정보 삭제
     * */
    @DeleteMapping("/api/v1/users/{id}")
    public Long delete(@PathVariable Long id){
        userService.delete(id);
        return id;
    }
}
