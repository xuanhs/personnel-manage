package com.xuanzjie.personnelmanage.service;

import com.xuanzjie.personnelmanage.BaseServiceTest;
import com.xuanzjie.personnelmanage.pojo.dto.FileBaseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

public class FileServiceTest extends BaseServiceTest {
    @Autowired
    FileService fileService;

    @DisplayName("insertDataTest")
    @ParameterizedTest
    @CsvSource("111")
    public void insertDataTest(Integer id){
        FileBaseDTO.UpdateFileBaseDTO fileBaseDTO = new FileBaseDTO.UpdateFileBaseDTO();
        fileBaseDTO.setFileName("测试名称");
        fileBaseDTO.setFilePath("test");
        fileBaseDTO.setMemoryName("保存名称");
        fileService.updateFileBase(fileBaseDTO);
    }

   /* @ParameterizedTest
    @MethodSource("initList")
    @DisplayName("setToStringTest")
    public void setToStringTest(List<Integer> integerList) {

        Set<Integer> set = new HashSet<>();
        set.addAll(integerList);
        String result = StringUtils.join(set, ",");
        Assert.assertEquals(result, "1234,123");
    }

    static Stream<Arguments> initList() {
        return Stream.of(
                arguments(Arrays.asList(123, 1234))
        );
    }*/
}
