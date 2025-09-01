package vn.wl.mes.formbean.response;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDto {
	private boolean status = true;
	private String message = "";
	private long timestamp;
	private Map<String, Object> data = new HashMap<>();
}
