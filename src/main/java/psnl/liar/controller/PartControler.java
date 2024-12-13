package psnl.liar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import psnl.liar.entity.Participants;
import psnl.liar.payload.dto.CreateUserDto;
import psnl.liar.service.PartService;

@RestController
@RequestMapping("/part")
@RequiredArgsConstructor
public class PartControler {

    private final PartService partService;

    // 참여자 등록
    @PostMapping("/create_user")
    public ResponseEntity addPart(@RequestBody CreateUserDto user) {
        Participants response = partService.createUser(user);
        return ResponseEntity.ok(response);
    }

}
