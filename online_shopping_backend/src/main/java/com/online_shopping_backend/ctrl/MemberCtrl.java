package com.online_shopping_backend.ctrl;

import com.online_shopping_backend.dto.MemberRegistrationDto;
import com.online_shopping_backend.entity.Members;
import com.online_shopping_backend.repo.MembersRepo;
import com.online_shopping_backend.util.MessageHelper;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.online_shopping_backend.dto.MemberUpdateDto;
import java.util.Map;

@RestController
@RequestMapping("/api/members")
public class MemberCtrl {

    @Autowired
    private MembersRepo membersRepo;

    @Autowired
    private MessageHelper messages;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> register(@Valid @RequestBody MemberRegistrationDto dto) {

        // MEM103's number-issuance logic: next available MEMBER_NO
        Integer nextNo = membersRepo.findMaxMemberNo().orElse(0) + 1;

        Members member = new Members();
        member.setMemberNo(nextNo);
        member.setUserName(dto.getUserName());
        member.setPwd(dto.getPwd());
        member.setAge(dto.getAge());
        member.setSex(dto.getSex());
        member.setZip(dto.getZip());
        member.setAddr(dto.getAddr());
        member.setTel(dto.getTel());
        // deleteFlag, regDate, lastUpd are all set automatically by @PrePersist

        Members saved = membersRepo.save(member);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "memberNo", saved.getMemberNo(),
                "message", messages.get("MSG001", saved.getMemberNo())
        ));
    }

    // MEM203/204: update the LOGGED-IN user's own info. Session-derived, not client-supplied ID
    @PutMapping("/me")
    @Transactional
    public ResponseEntity<?> updateOwnInfo(@Valid @RequestBody MemberUpdateDto dto, HttpSession session) {
        Integer memberNo = (Integer) session.getAttribute("memberNo");

        if (memberNo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("code", "MSG012", "message", messages.get("MSG012")));
        }

        Members member = membersRepo.findById(memberNo).orElse(null);

        if (member == null || "1".equals(member.getDeleteFlag())) {
            session.invalidate();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("code", "MSG012", "message", messages.get("MSG012")));
        }

        // MSG002: cannot change password to the same one as before
        if (dto.getPwd() != null && dto.getPwd().equals(member.getPwd())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("code", "MSG002", "message", messages.get("MSG002")));
        }

        member.setUserName(dto.getUserName());
        if (dto.getPwd() != null && !dto.getPwd().isBlank()) {
            member.setPwd(dto.getPwd());
        }
        member.setAge(dto.getAge());
        member.setSex(dto.getSex());
        member.setZip(dto.getZip());
        member.setAddr(dto.getAddr());
        member.setTel(dto.getTel());

        Members saved = membersRepo.save(member);

        // keep session's display name in sync if it changed
        session.setAttribute("userName", saved.getUserName());

        return ResponseEntity.ok(Map.of(
                "message", messages.get("MSG003")
        ));
    }

    // MEM201: display the LOGGED-IN user's own info, dont accepts a member number from the client
    @GetMapping("/me")
    public ResponseEntity<?> getOwnInfo(HttpSession session) {
        Integer memberNo = (Integer) session.getAttribute("memberNo");

        if (memberNo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("code", "MSG012", "message", messages.get("MSG012")));
        }

        Members member = membersRepo.findById(memberNo).orElse(null);

        if (member == null || "1".equals(member.getDeleteFlag())) {
            // session refers to a member that no longer exists / was deleted
            session.invalidate();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("code", "MSG012", "message", messages.get("MSG012")));
        }

        return ResponseEntity.ok(Map.of(
                "memberNo", member.getMemberNo(),
                "userName", member.getUserName(),
                "age", member.getAge(),
                "sex", member.getSex(),
                "zip", member.getZip(),
                "addr", member.getAddr(),
                "tel", member.getTel(),
                "regDate", member.getRegDate().toString()
        ));
    }

    // MEM301: soft-delete the LOGGED-IN user's own account, then discard the session.
    @DeleteMapping("/me")
    @Transactional
    public ResponseEntity<?> deleteOwnInfo(HttpSession session) {
        Integer memberNo = (Integer) session.getAttribute("memberNo");

        if (memberNo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("code", "MSG012", "message", messages.get("MSG012")));
        }

        Members member = membersRepo.findById(memberNo).orElse(null);

        if (member == null || "1".equals(member.getDeleteFlag())) {
            session.invalidate();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("code", "MSG012", "message", messages.get("MSG012")));
        }

        //  delete flag + last-updated change. Everything else stays untouched.
        member.setDeleteFlag("1");
        membersRepo.save(member);   // @PreUpdate refreshes lastUpd automatically

        // Discard session
        session.invalidate();

        return ResponseEntity.ok(Map.of(
                "message", messages.get("MSG004")
        ));
    }


}