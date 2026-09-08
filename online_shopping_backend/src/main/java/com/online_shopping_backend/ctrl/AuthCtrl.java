package com.online_shopping_backend.ctrl;

import com.online_shopping_backend.entity.Members;
import com.online_shopping_backend.entity.Staff;
import com.online_shopping_backend.repo.MembersRepo;
import com.online_shopping_backend.repo.StaffRepo;
import com.online_shopping_backend.util.MessageHelper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthCtrl {

    @Autowired
    private MembersRepo userRepo;

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private MessageHelper messages;

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "Logged out"));
    }

    @PostMapping("/member-login")
    public ResponseEntity<?> memberLogin(@RequestBody Map<String, String> body, HttpSession session) {
        String memberNoStr = body.get("memberNo");
        String password = body.get("password");

        Integer memberNo;
        try {
            memberNo = Integer.parseInt(memberNoStr);
        } catch (NumberFormatException | NullPointerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("code", "MSG012", "message", messages.get("MSG012")));
        }

        Members member = userRepo.findById(memberNo).orElse(null);

        // bundled existence check: member exists AND password matches AND not soft-deleted
        boolean valid = member != null
                && member.getPwd().equals(password)
                && "0".equals(member.getDeleteFlag());

        if (!valid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("code", "MSG012", "message", messages.get("MSG012")));
        }

        session.setAttribute("memberNo", member.getMemberNo());
        session.setAttribute("userName", member.getUserName());

        return ResponseEntity.ok(Map.of(
                "memberNo", member.getMemberNo(),
                "userName", member.getUserName()
        ));
    }

    @PostMapping("/staff-login")
    public ResponseEntity<?> staffLogin(@RequestBody Map<String, String> body, HttpSession session) {
        String staffNoStr = body.get("staffNo");
        String password = body.get("password");

        Integer staffNo;
        try {
            staffNo = Integer.parseInt(staffNoStr);
        } catch (NumberFormatException | NullPointerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("code", "MSG012", "message", messages.get("MSG012")));
        }

        Staff staff = staffRepo.findById(staffNo).orElse(null);

        // no DELETE_FLG on ONLINE_STAFF, so just existence + password match
        boolean valid = staff != null && staff.getPwd().equals(password);

        if (!valid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("code", "MSG012", "message", messages.get("MSG012")));
        }

        session.setAttribute("staffNo", staff.getStaffNo());
        session.setAttribute("staffName", staff.getStaffName());

        return ResponseEntity.ok(Map.of(
                "staffNo", staff.getStaffNo(),
                "staffName", staff.getStaffName()
        ));
    }
}