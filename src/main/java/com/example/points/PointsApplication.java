package com.example.points;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
public class PointsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PointsApplication.class, args);
	}

}

@RequestMapping("/points/{username}")
@RestController
class PointsRestController {

	private final Map<String, Integer> db = new ConcurrentHashMap<>();

	private Map<String, Object> pointsFor(String u) {
		return pointsFor(u, this.db.get(u));
	}

	private Map<String, Object> pointsFor(String u, int i) {
		return Map.of("username", u, "points", i);
	}

	@GetMapping
	ResponseEntity<Map<String, Object>> read(@PathVariable String username) {
		if (this.db.containsKey(username)) {
			return ResponseEntity.of(Optional.of(pointsFor(username)));
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	Map<String, Object> write(@PathVariable String username,
																											@RequestBody Map<String, Integer> points) {
		this.db.put(username, points.get("points"));
		return this.pointsFor(username);
	}
}