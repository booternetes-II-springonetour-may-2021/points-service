package com.example.points;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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


	private final PointsService pointsService;

	PointsRestController(PointsService pointsService) {
		this.pointsService = pointsService;
	}

	@GetMapping
	ResponseEntity<Map<String, Object>> read(@PathVariable String username) {
		Map<String, Object> points = pointsService.pointsFor(username);
		if (!points.isEmpty()) {
			return ResponseEntity.of(Optional.of(points));
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	Map<String, Object> write(@PathVariable String username, @RequestBody Map<String, Integer> points) {
		int add = points.get("points");
		return pointsService.addPoints(username, add);
	}
}

@Service
class PointsService {
	private final Map<String, Integer> db = new ConcurrentHashMap<>();

	Map<String, Object> addPoints(String username, int points) {
		int existing = (int) pointsFor(username).get("points");
		this.db.put(username, existing + points);
		return this.pointsFor(username);
	}

	Map<String, Object> pointsFor(String u) {
		return pointsFor(u, this.db.getOrDefault(u, 0));
	}

	private Map<String, Object> pointsFor(String u, int i) {
		return Map.of("username", u, "points", i);
	}


}