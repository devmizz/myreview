package com.myplace.myreview.place.view;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.google.gson.Gson;
import com.myplace.myreview.place.controller.PlaceController;
import com.myplace.myreview.place.dto.PlaceForm;
import com.myplace.myreview.place.service.PlaceProvider;
import com.myplace.myreview.place.service.PlaceService;
import org.junit.jupiter.api.Test;
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

    @Test
    public void testPlaceAddPostMethod() throws Exception {
        // given
        Gson gson = new Gson();

        PlaceForm placeForm = new PlaceForm();
        placeForm.setName("장충동 왕족발");
        placeForm.setAddress("어린이대공원역 어쩌고");
        placeForm.setReview("맛있었어");
        placeForm.setGrade(5);

        // when, then
        mockMvc.perform(
                post("/places/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(placeForm))
            ).andExpect(status().isFound())
            .andExpect(view().name("redirect:/"));
    }
}
