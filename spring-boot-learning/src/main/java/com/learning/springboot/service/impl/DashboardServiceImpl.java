package com.learning.springboot.service.impl;

import com.learning.springboot.repository.DashboardRepository;
import com.learning.springboot.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired private DashboardRepository dashboardRepository;

    @Override
    public Map<String, Object> summary() {
        return dashboardRepository.dashboardSummary();
    }

    @Override
    public List<Map<String, Object>> last7DaysRegistrations() {
        return dashboardRepository.last7DaysRegistrations();
    }

    @Override
    public List<Map<String, Object>> last7DaysOperations() {
        return dashboardRepository.last7DaysOperations();
    }

    @Override
    public List<Map<String, Object>> userStatusDistribution() {
        return dashboardRepository.userStatusDistribution();
    }
}