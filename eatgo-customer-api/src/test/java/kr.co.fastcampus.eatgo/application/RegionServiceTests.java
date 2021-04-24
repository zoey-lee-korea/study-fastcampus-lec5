package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Region;
import kr.co.fastcampus.eatgo.domain.RegionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

public class RegionServiceTests {

    private RegionService regionService;

    @Mock
    private RegionRepository regionRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockRegionRepository();
        regionService = new RegionService(regionRepository);
    }

    public void mockRegionRepository() {
        List<Region> regions = new ArrayList<>();
        regions.add(Region.builder().name("seoul").build());
        given(regionRepository.findAll()).willReturn(regions);
    }

    @Test
    public void getRegions() {
        List<Region> regions = regionService.getRegions();
        Region region = regions.get(0); // 길이나 첫번째 요소를 검증한다
        assertThat(region.getName(),is("seoul"));
    }


}