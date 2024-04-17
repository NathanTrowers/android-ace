package com.not.androidace;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class QuizRunThroughInstrumentedTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testQuizWhenOneResponseIsRight() {
        /* Act */
        onView(withId(R.id.button_start)).perform(click());
        onView(withId(R.id.button_question_1_option_1)).perform(click());
        onView(withId(R.id.button_question_1_option_4)).perform(click()); // correct answer
        onView(withId(R.id.button_question_2_next)).perform(click());

        onView(withId(R.id.checkBox_question_2_option_1)).perform(click()); //wrong answer
        onView(withId(R.id.button_question_3_next)).perform(click());

        onView(withId(R.id.radioButton_question3_option_1)).perform(click()); //wrong answer
        onView(withId(R.id.button_question_4_next)).perform(click());

        onView(withId(R.id.imageView_question_4_option_1)).perform(click()); //wrong answer
        onView(withId(R.id.button_question_5_next)).perform(click());

        onView(ViewMatchers.withId(R.id.list_question_five_options))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click())); //wrong answer
        onView(withId(R.id.button_score_next)).perform(click());

        /* Assert */
        onView(withId(R.id.textview_score)).check(matches(withText("1")));
        onView(withId(R.id.textview_score_message)).check(matches(withText("Please try again.")));
        onView(withId(R.id.button_take_new_quiz)).check(matches(withText("TRY AGAIN")));
    }

    @Test
    public void testQuizWhenTwoResponsesAreRight() {
        /* Act */
        onView(withId(R.id.button_start)).perform(click());
        onView(withId(R.id.button_question_1_option_1)).perform(click());
        onView(withId(R.id.button_question_1_option_4)).perform(click()); // correct answer
        onView(withId(R.id.button_question_2_next)).perform(click());

        onView(withId(R.id.checkBox_question_2_option_1)).perform(click());
        onView(withId(R.id.checkBox_question_2_option_4)).perform(click()); // correct answer
        onView(withId(R.id.button_question_3_next)).perform(click());

        onView(withId(R.id.radioButton_question3_option_1)).perform(click()); //wrong answer
        onView(withId(R.id.button_question_4_next)).perform(click());

        onView(withId(R.id.imageView_question_4_option_1)).perform(click()); //wrong answer
        onView(withId(R.id.button_question_5_next)).perform(click());

        onView(ViewMatchers.withId(R.id.list_question_five_options))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click())); //wrong answer
        onView(withId(R.id.button_score_next)).perform(click());

        /* Assert */
        onView(withId(R.id.textview_score)).check(matches(withText("2")));
        onView(withId(R.id.textview_score_message)).check(matches(withText("Please try again.")));
        onView(withId(R.id.button_take_new_quiz)).check(matches(withText("TRY AGAIN")));
    }

    @Test
    public void testQuizWhenThreeResponsesAreRight() {
        /* Act */
        onView(withId(R.id.button_start)).perform(click());
        onView(withId(R.id.button_question_1_option_1)).perform(click());
        onView(withId(R.id.button_question_1_option_4)).perform(click()); // correct answer
        onView(withId(R.id.button_question_2_next)).perform(click());

        onView(withId(R.id.checkBox_question_2_option_1)).perform(click());
        onView(withId(R.id.checkBox_question_2_option_4)).perform(click()); // correct answer
        onView(withId(R.id.button_question_3_next)).perform(click());

        onView(withId(R.id.radioButton_question3_option_1)).perform(click());
        onView(withId(R.id.radioButton_question3_option_4)).perform(click()); // correct answer
        onView(withId(R.id.button_question_4_next)).perform(click());

        onView(withId(R.id.imageView_question_4_option_1)).perform(click()); //wrong answer
        onView(withId(R.id.button_question_5_next)).perform(click());

        onView(ViewMatchers.withId(R.id.list_question_five_options))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click())); //wrong answer
        onView(withId(R.id.button_score_next)).perform(click());

        /* Assert */
        onView(withId(R.id.textview_score)).check(matches(withText("3")));
        onView(withId(R.id.textview_score_message)).check(matches(withText("Good job!")));
        onView(withId(R.id.button_take_new_quiz)).check(matches(withText("TAKE ANOTHER QUIZ")));
    }

    @Test
    public void testQuizWhenFourResponsesAreRight() {
        /* Act */
        onView(withId(R.id.button_start)).perform(click());
        onView(withId(R.id.button_question_1_option_1)).perform(click());
        onView(withId(R.id.button_question_1_option_4)).perform(click()); // correct answer
        onView(withId(R.id.button_question_2_next)).perform(click());

        onView(withId(R.id.checkBox_question_2_option_1)).perform(click());
        onView(withId(R.id.checkBox_question_2_option_4)).perform(click()); // correct answer
        onView(withId(R.id.button_question_3_next)).perform(click());

        onView(withId(R.id.radioButton_question3_option_1)).perform(click());
        onView(withId(R.id.radioButton_question3_option_4)).perform(click()); // correct answer
        onView(withId(R.id.button_question_4_next)).perform(click());

        onView(withId(R.id.imageView_question_4_option_1)).perform(click());
        onView(withId(R.id.imageView_question_4_option_3)).perform(click()); //correct answer
        onView(withId(R.id.button_question_5_next)).perform(click());

        onView(ViewMatchers.withId(R.id.list_question_five_options))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click())); //wrong answer
        onView(withId(R.id.button_score_next)).perform(click());

        /* Assert */
        onView(withId(R.id.textview_score)).check(matches(withText("4")));
        onView(withId(R.id.textview_score_message)).check(matches(withText("Excellent work!")));
        onView(withId(R.id.button_take_new_quiz)).check(matches(withText("TAKE ANOTHER QUIZ")));
    }

    @Test
    public void testQuizWhenAllResponsesAreRightAndTakeAnotherQuizClicked() {
        /* Act */
        onView(withId(R.id.button_start)).perform(click());
        onView(withId(R.id.button_question_1_option_1)).perform(click());
        onView(withId(R.id.button_question_1_option_4)).perform(click()); // correct answer
        onView(withId(R.id.button_question_2_next)).perform(click());

        onView(withId(R.id.checkBox_question_2_option_1)).perform(click());
        onView(withId(R.id.checkBox_question_2_option_4)).perform(click()); // correct answer
        onView(withId(R.id.button_question_3_next)).perform(click());

        onView(withId(R.id.radioButton_question3_option_1)).perform(click());
        onView(withId(R.id.radioButton_question3_option_4)).perform(click()); // correct answer
        onView(withId(R.id.button_question_4_next)).perform(click());

        onView(withId(R.id.imageView_question_4_option_1)).perform(click());
        onView(withId(R.id.imageView_question_4_option_3)).perform(click()); //correct answer
        onView(withId(R.id.button_question_5_next)).perform(click());

        onView(ViewMatchers.withId(R.id.list_question_five_options))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click())); //correct answer
        onView(withId(R.id.button_score_next)).perform(click());

        /* Assert */
        onView(withId(R.id.textview_score)).check(matches(withText("5")));
        onView(withId(R.id.textview_score_message)).check(matches(withText("You are a genius!")));
        onView(withId(R.id.button_take_new_quiz)).check(matches(withText("TAKE ANOTHER QUIZ")));
        onView(withId(R.id.button_take_new_quiz)).perform(click());

        onView(withId(R.id.button_question_2_next)).check(matches(withText("Next Question")));
    }

    @Test
    public void testQuizWhenAllResponsesAreWrong() {
        /* Act */
        onView(withId(R.id.button_start)).perform(click());
        onView(withId(R.id.button_question_1_option_1)).perform(click()); //wrong answer
        onView(withId(R.id.button_question_2_next)).perform(click());

        onView(withId(R.id.checkBox_question_2_option_1)).perform(click()); //wrong answer
        onView(withId(R.id.button_question_3_next)).perform(click());

        onView(withId(R.id.radioButton_question3_option_1)).perform(click()); //wrong answer
        onView(withId(R.id.button_question_4_next)).perform(click());

        onView(withId(R.id.imageView_question_4_option_1)).perform(click()); //wrong answer
        onView(withId(R.id.button_question_5_next)).perform(click());

        onView(ViewMatchers.withId(R.id.list_question_five_options))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click())); //wrong answer
        onView(withId(R.id.button_score_next)).perform(click());

        /* Assert */
        onView(withId(R.id.textview_score)).check(matches(withText("0")));
        onView(withId(R.id.textview_score_message)).check(matches(withText("Please try again.")));
        onView(withId(R.id.button_take_new_quiz)).check(matches(withText("TRY AGAIN")));
    }
}
