package com.siw.project.sevices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.siw.project.models.UsageRequest;
import com.siw.project.repository.UsageRequestRepository;

@Service
public class UsageRequestService {
	
	@Autowired
	private UsageRequestRepository usageRequestRepository;
	
	@Transactional
	public UsageRequest add(UsageRequest usageRequest) {
		return this.usageRequestRepository.save(usageRequest);
	}
	
	public List<UsageRequest> all() {
		return (List<UsageRequest>) usageRequestRepository.findAll();
	}
	
	public UsageRequest findById(Long id) {
		return usageRequestRepository.findById(id).get();
	}


}
