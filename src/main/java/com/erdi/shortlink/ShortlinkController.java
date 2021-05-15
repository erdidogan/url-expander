package com.erdi.shortlink;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping(value = "api/v1", consumes = "application/json", produces = "application/json")
@RequiredArgsConstructor
public class ShortlinkController {

    private final ShortlinkService shortlinkService;

    @PostMapping("shortlink")
    @CrossOrigin
    public ResponseEntity<?> saveShortlink(@Valid @RequestBody Shortlink shortlink,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            HashMap<String, Object> response = new HashMap<>();
            response.put("message", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            return ResponseEntity.badRequest().body(response);
        }
        Shortlink response = shortlinkService.shortlinkOps(shortlink);
        return ResponseEntity.ok().body(response);

    }

}
