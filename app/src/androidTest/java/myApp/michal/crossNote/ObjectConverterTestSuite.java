package myApp.michal.crossNote;

import myApp.michal.crossNote.Converter.ObjectConverter;
import myApp.michal.crossNote.Code.Classes.TrainingBuilder;

import org.junit.Before;
import org.junit.Test;

public class ObjectConverterTestSuite implements GlobalConstant{

    private ObjectConverter objectConverter;

    @Before
    public void SetUp(){
        objectConverter = new ObjectConverter();
    }

    @Test
    public void verifyDecodedObjectWithSucceed(){
        TrainingBuilder l_in = Utility.createTraining(DATE21MAR2017, TIME_21, AMRAP, MOTIONS_LIST, RESULT_AMRAP, GLO_FALSE);
        String convert = objectConverter.objectToString(l_in);
        TrainingBuilder l_out = (TrainingBuilder) objectConverter.stringToObject(convert);
        Utility.objectMatcher(l_in, l_out);
    }
}
