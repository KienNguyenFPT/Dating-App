package org.kiennguyenfpt.datingapp.controllers;

import org.kiennguyenfpt.datingapp.dtos.requests.CafeRequest; // Thêm import
import org.kiennguyenfpt.datingapp.dtos.responses.CafeResponse; // Thêm import
import org.kiennguyenfpt.datingapp.responses.CommonResponse;
import org.kiennguyenfpt.datingapp.services.CafeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cafes")
@CrossOrigin

public class CafeController {
    private final CafeService cafeService;

    public CafeController(CafeService cafeService) {
        this.cafeService = cafeService;
    }

    @PostMapping
    public ResponseEntity<CafeResponse> createCafe(@RequestBody CafeRequest cafeRequest) {
        return ResponseEntity.ok(cafeService.createCafe(cafeRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CafeResponse> updateCafe(@PathVariable Long id, @RequestBody CafeRequest cafeRequest) {
        return ResponseEntity.ok(cafeService.updateCafe(id, cafeRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCafe(@PathVariable Long id) {
        return ResponseEntity.ok(cafeService.deleteCafe(id)); // Thay đổi để trả về String
    }

    @GetMapping
    public ResponseEntity<List<CafeResponse>> getAllCafes() {
        return ResponseEntity.ok(cafeService.getAllCafes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CafeResponse> getCafeById(@PathVariable Long id) {
        return ResponseEntity.ok(cafeService.getCafeById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<CommonResponse<List<CafeResponse>>> searchCafes(@RequestParam String name) {
        CommonResponse<List<CafeResponse>> response = new CommonResponse<>();
        List<CafeResponse> cafes = cafeService.searchCafesByName(name);

        if (cafes.isEmpty()) {
            // Nếu không tìm thấy quán, trả về 200 và thông điệp "Không tìm thấy"
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Không tìm thấy quán cà phê với tên: " + name);
            response.setData(cafes);
            return ResponseEntity.ok(response);
        }

        // Nếu tìm thấy, trả về danh sách quán
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Tìm kiếm thành công.");
        response.setData(cafes);
        return ResponseEntity.ok(response);
    }

}
