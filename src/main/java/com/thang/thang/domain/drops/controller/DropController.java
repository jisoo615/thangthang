package com.thang.thang.domain.drops.controller;

import com.thang.thang.domain.drops.dto.DropRequest;
import com.thang.thang.domain.drops.service.DropService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Path;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/drops")
public class DropController {

    private final DropService dropService;

    @PostMapping
    public ResponseEntity createDrops(DropRequest dropRequest){// 드랍 등록

        return ResponseEntity.ok().body(dropService.createDropInfo(dropRequest));
    }

    @GetMapping
    public ResponseEntity getDrops(){// 드랍 전체 조회
        
        return ResponseEntity.ok().body(dropService.findAllDrops());
    }

    @GetMapping("/{dropId}")
    public ResponseEntity getOneDrop(@PathVariable Long  dropId){// 드랍 개별 조회

        return ResponseEntity.ok().body(dropService.findDropById(dropId));
    }
}
