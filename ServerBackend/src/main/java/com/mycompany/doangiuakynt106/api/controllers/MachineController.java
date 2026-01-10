package com.mycompany.doangiuakynt106.api.controllers;

import com.mycompany.doangiuakynt106.api.dto.MachineState;
import com.mycompany.doangiuakynt106.services.MachineService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/machines")
public class MachineController {

    private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping
    public List<MachineState> list() {
        return machineService.listMachines();
    }

    @GetMapping("/{machineId}")
    public MachineState get(@PathVariable String machineId) {
        return machineService.getOrCreate(machineId);
    }

    @PostMapping("/{machineId}/start")
    public ResponseEntity<?> start(@PathVariable String machineId, HttpServletRequest req) {
        try {
            String username = (String) req.getAttribute("username");
            MachineState state = machineService.startSession(machineId, username);
            return ResponseEntity.ok(state);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(403).body(Map.of("error", Map.of("message", e.getMessage(), "code", 403)));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", Map.of("message", e.getMessage(), "code", 400)));
        }
    }

    @PostMapping("/{machineId}/lock")
    public MachineState lock(@PathVariable String machineId) {
        return machineService.lockMachine(machineId);
    }

    @PostMapping("/{machineId}/pause")
    public MachineState pause(@PathVariable String machineId) {
        return machineService.pause(machineId);
    }

    @PostMapping("/{machineId}/resume")
    public MachineState resume(@PathVariable String machineId) {
        return machineService.resume(machineId);
    }

    @PostMapping("/switch")
    public ResponseEntity<?> switchMachine(@RequestBody Map<String, String> body) {
        try {
            String fromId = body.get("fromMachineId");
            String toId = body.get("toMachineId");
            if (fromId == null || toId == null) {
                return ResponseEntity.badRequest().body(Map.of("error", Map.of("message", "fromMachineId_toMachineId_required", "code", 400)));
            }
            return ResponseEntity.ok(machineService.switchMachine(fromId, toId));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", Map.of("message", e.getMessage(), "code", 400)));
        }
    }
}
