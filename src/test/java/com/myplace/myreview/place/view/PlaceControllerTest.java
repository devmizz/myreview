package com.myplace.myreview.place.view;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.myplace.myreview.place.controller.PlaceController;
import com.myplace.myreview.place.service.PlaceProvider;
import com.myplace.myreview.place.service.PlaceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@WebMvcTest(PlaceController.class)
public class PlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PlaceService placeService;

    @MockBean
    PlaceProvider placeProvider;

    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("resources/templates");
        viewResolver.setSuffix(".html");

        return viewResolver;
    }

    @Test
    public void testPlaceAddView() throws Exception {
        mockMvc.perform(
            get("/places/add")
                .accept(MediaType.parseMediaType("application/html;charset=UTF-8")))
            .andExpect(status().isOk())
            .andExpect(view().name("/places/add"));
    }
}
