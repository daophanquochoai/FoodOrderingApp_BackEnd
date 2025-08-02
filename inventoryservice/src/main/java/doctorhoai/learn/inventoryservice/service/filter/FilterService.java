package doctorhoai.learn.inventoryservice.service.filter;

import doctorhoai.learn.inventoryservice.dto.filter.FilterOption;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface FilterService {
    Map<String, Object> getFilter(FilterOption option);
}
