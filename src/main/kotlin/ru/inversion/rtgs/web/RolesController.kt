package ru.inversion.rtgs.web

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.inversion.rtgs.entity.enums.ERole

/**
 * @author Dmitry Hvastunov
 * @created 26.05.2021
 * @project rtgs
 */

@CrossOrigin
@RestController
@RequestMapping("/api/roles")
@PreAuthorize("permitAll()")
class RolesController {

    @GetMapping("all")
    fun getAllRoles(): ResponseEntity<List<ERole>> {
        return ResponseEntity.ok(ERole.values().toList())
    }
}