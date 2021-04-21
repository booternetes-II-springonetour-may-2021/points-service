package com.example.points;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	Map<String, Object> write(@PathVariable String username, @RequestBody Map<String, Object> points) {
		int add = (Integer) points.get("points");
		int existing = this.db.getOrDefault(username, 0);
		this.db.put(username, existing + add);
		return this.pointsFor(username);
	}
}