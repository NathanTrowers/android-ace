package com.not.androidace.service;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Intent;
import android.os.IBinder;

import androidx.test.filters.MediumTest;
import androidx.test.rule.ServiceTestRule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@MediumTest
@RunWith(Parameterized.class)
public class QuestionServiceIntegrationTest {
    @Rule
    public final ServiceTestRule mServiceRule = new ServiceTestRule();

    @Parameters
    public static Iterable<Object[]> getMockQuestionSet() {
        Map<String, Object> questionOne = new HashMap<>();
        questionOne.put("question", "What is the purpose of the 'AndroidManifest.xml' file?");
        questionOne.put("answer", "To describe information about the app to the android build tools, android OS, and Google Play.");
        questionOne.put("options", new String[]{
                "It turns the mobile app into a web app.",
                "It makes the app have a responsive UI.",
                "To create the app.",
                "To describe information about the app to the android build tools, android OS, and Google Play."
        });

        Map<String, Object> questionTwo = new HashMap<>();
        questionTwo.put("question", "How do we set an xml file from the 'layout' folder on display using Java code?");
        questionTwo.put("answer", "Use the 'setContentView' method.");
        questionTwo.put("options", new String[]{
                "Reference it in the 'AndroidManifest.xml' file.",
                "Use a 'System.out.print' statement.",
                "Use the 'findViewById' method.",
                "Use the 'setContentView' method."
        });
        Map<String, Object> questionThree = new HashMap<>();
        questionThree.put("question", "What is the 'Intent' Java class used for?");
        questionThree.put("answer", "To initiate app operations.");
        questionThree.put("options", new String[]{
                "To create action-oriented event listeners.",
                "It asks the program what the user wants to do.",
                "To generate xml layouts.",
                "To initiate app operations."
        });
        Map<String, Object> questionFour = new HashMap<>();
        questionFour.put("question", "Where of these is a widget?");
        questionFour.put("answer", "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");
        questionFour.put("options", new String[]{
                "iVBORw0KGgoAAAANSUhEUgAAAFEAAAAoCAIAAAAJwIFoAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAASdEVYdFNvZnR3YXJlAEdyZWVuc2hvdF5VCAUAAAOiSURBVGhD7ZrdL9tRGMf9Id4JETJdK25cVIULgsS0ZkhwgcTFJDWZNpmLSsaWkRnmrSo1Sa0ZmbgZmWQkM9RLqZdmUW7atapW6rWu9u0OR9OLsqZpsp/fk2/S53n6nNPf53eec35t0qDL+2f3m9l2cGA2m38xUXt7e+fn51eclPm33b65tcVgGXZ2Dg8Pj4+PnU6ni/ni4sJssXgUMUxbej1WG9hY8CAAn56eGk0mjyLmaXt722q1OhwOF/PJyYnRaPSoYJ5gFosFS80yM1osM8vMVLHMLDNT5SPz62traW39NDKysbmJ5NLyMjJfJiZoWXdPj0KhcK93t89jY0ql8ipwM4yiM2C2t21tvX19msVFmsRAlH2fnaUZtVqNDA29y0fm4ODg2NjY5ORkLo8XGhqanZ2tXV39Nj2NPO4CLeOnpubk5MBBJTEUxMXFEf9lU1NlZSXxw8LCoqOjiS8SiTBEp9Ph3ZCQkEQOJyYmJj4+Xt7fT6aVSKWYp7y8nIRQdXU1MjT0Lt+Z8THEByRCrIYXZiK0AwokEgnNUHE4nMeFhe4ZsVgcERHR2dkJX6vVCkWiqKior1NTCAkzrKe3lxQHmvnD0BDC9o4OPzKvaLVJSUllZWU0M7+wEBkZKa6thU+YUcDlcjUaDTIBYkYTFhUXC4VCdB0uTre+7kfmH3Nz4eHhssZGmoFSUlJIDWHuVyiwI0pKSjBtgJhxBVVVVRUVFTweLz09HevgR2acT9jJTc3NNAMJBIJH+flwCPP0zMyLhgZgKwYGAt3bE5OTCKVSKWF+09JCy/h8fm5uLg3vzow7iHPLoxKdXFpaCocyYwukCgQY+6SoCBn3Yi/yA/Pc/DzCpzU1WBycvfXXF7q8soIWIFdJdHfm9Y2NjIwMtAmoSEalUmHsq78PJMoMf3x8HGcbVhsZUnmrfGfOy8uTy+Vd3d1YSXzkR7UaF4rtDWys+fuuroKCAtJ4dNTdmSHV8DBgMjMz37W3y2SyhIQE3IXVtTW85c4MPa+vRwgj4a3ynZkYqNLS0uiTc3FpCcAPuVycQJlZWcrBQToE+idmaGR0lNzEB4mJz+rqcLCRvAczvhpgqyNDwlvlI/N/LZaZZWaqWGaWmalimVlmpsqT2Ww2e1QwTPidf8N8dnaGF4PBsKbT4dcCIwVgvV5vMplsNtvR0ZGLGS+IfzLadnd3b5hJb9vtdivTbX9/Hx0NWNd/K5xOJ1bbcQ/M9U+ay8s/P/vxADW/NNMAAAAASUVORK5CYII=",
                "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",
                "iVBORw0KGgoAAAANSUhEUgAAA8EAAAB1CAIAAADhmXSpAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAASdEVYdFNvZnR3YXJlAEdyZWVuc2hvdF5VCAUAABEISURBVHhe7d19bBvnYcdxW3UdC54rIzMKG64Rz/Osps7SIglibEmXIQWWNEGDomgN5I8tCVAE2Nq0KGo7XY0CWTMUTZMA/atYkQ3L/qzkYU1jPpfYlp1EcvyWOZL8IvkFaWRJs95MUqIp8p4j+ex57k4USZG0jrLFo/z94IfD6Xgi+Zf8w+PnnmeZrLf1G/5CzZG2nUcf/UbX0RP+TQAAAEBohLRDa/F4ghoNAACAEApvh9ZGRsao0QAAAAibUHdobWBgUNdo/1YAAAAgBMLVoa9fT3knujcXxr8VAAAACIEQdWhdoFevXv/2/kP6/Gtfe+r3v4/4dwAAAABhEpYO7RXorqMn7rlnh/5xYGBQn/h3AAAAAGESig6dL9D6xyef3PnBByd0jdYnDEUDAAAghOrfoR999Bv5Aq3pk9YvPqA79Pnz/ffe+1feRQAAACA86t+h53ryyZ26QOsaff/9j/iXAAAAgNAIY4ceuDLY+sUHnn32n06eOu1fAgAAAEIjjB0aAAAACDM6NAAAABAMHRoAAAAIhg4NAAAABEOHBgAAAIKhQwMAAADB0KEBAACAYOjQAAAAQDB0aABoGLZjUsixTSqp/uqMtBsAQAB0aABoGHRoAAgJOjQANJxqrXd+vbkQHRoAAqNDA0DDoUMDQJ3RoQGggWTcI60XAOqMDg0ADcTr0BodGgDqiQ4NAA1s7lOGAIBFQIcGgAZGhwaAuqBDA8Dty3Eo4ABQCzo0ANy+bDvYEh4AAA8dGgAaT8n4ccl6dvlmzDAzANwidGgAaBhlO7Ej0znnek7GlJxQckzJESWvKjnsRp/rKxP61axM6jv93ynGaDQABEWHBoAGUGXnlKxMuKX5orJ7lH1S2Z1KHlbygJKHlNTn+spp86oc03cW1ei07b0nw9UAEBQdGgAagNeh3QHjtCOTORk1w8z2JSXPKeeUGv1dqu+1RM9P4x/94FrnMyMd3x6wvj5y+FvjXc/ETr1wvXdvsv81Ndqu5HElzyh52R2injAj03RoAKgJHRoAGkC+5uoCrTLXlOxTqYgafiXT89y1Qw9Hxfa42DolNifEXdPWF5Jig5eEtTEhNunr+lV9T/TgQ073s2roV+Z3ZY+SY6aRmx7Nji0AEAwdGgDCL+3I6ZycdMeP+1T6/VT/L69Yj41H7tVFWYrVWbFCiWW5yDJ9VPronRQnK5r0nUmxcUJ8Wf9uuv9llXrPvJsczsmYfn//owAA80CHBoC6S1cfCdYFN2sK9CfKPqQu7h1/9+G42Krbsy3WZkSzW6CbShpz2Sat79T3pyMtSWt9zNo2ceCr6tJP3cnTF3WNNh/kjknziCEA3BAdGgDqrqhDF1fYdFYm3eU1erJX3hg7sjMqtuvqnNWl2VpeUpGLUmE0Op+MWKnfJyZaRzq+kxt4w53acTVjT1Vv8wAADx0aAEKizGi0mf0sx5Tzv+rCT8bFfQmxyRYtOesO04MrteQbtWcvuoXr97HFmqTYOBa5P9v/M+V0KXvEe8oQAFAdHRoAQqKoQztmBDqh7EEljyW697jDz2uyYoU/6VmnSocuuXKjpMTaCfGV6e4fKnlUpa/oz/W/BACgAjo0AISLtwSHu+rzoJp8Z+rDp2ORP9c1t/y858rJWrMn+fN8Zrt4wbwO/Vlqcp/+XO+bAAAqoUMDQNiYVThMgZad1449H7c2645rJkAXF9+5PxamsDcXdejKs6gd60+mxJbJY0+b0WgzN/o660YDQCV0aAAIF3cZu6iSH9ndz8esrdPic/kCPVuFg0zYKOrQXvSvF7yD6eL6zcUKKVbHRKuZ1OF8oOSI/4WK2Y4JANzm6NAAEC5KTijZY1/4RdS6W4rmjLUi34Bz78yMItfaof2ha7c0Fw1j7/ff050b/eXshb36O5RdN5oODQAaHRoA6q7oaUKzDvSVV0etB6atdaZAV5oD7fbgQDOk50T/btPM+/jRn2iLlnFxX+7Kb911o6NmbknRpI6MGwC4rdGhAaDu/A49M4vjWOzwY6ZAi1WZ2b47J7MduuYaXdyhC6ZKJ8WGsSM7lTyk5HBhv3fRoQGADg0A9VMyL8It0OfkpVdiotWJ3FFxBLp68p245Hwe8WZ36OJuizX6O6jL/6xk35wODQCgQwNA/RR2aMdxzFocqf3/d/CJpLW+sNoGywI6tI735GJGrExZLRMHH1LpI0qOOTLJBuAAUIgODQCh4Mi0kn2q70dxa4sUa5RoKnwWsDCVrlfMfJp08T1Z0aRrdMzaZl/4V3cb8AnWuQOAQnRoAKg/dyb0pJruHBU7dIHORD5b3w7tJWmtv2I9plIRd1Y0AGAWHRoA6i+rC7QzoIbfjFp3z2M/QvMsYFGTnk9LLpPiZwrnxHbXuVNDv1Lysv6STOcAgLyFdGizk1ZWJnJyMidjlRN1U3KREEJu6ygnXvSj2ZXwo8mP90yJzSVFtlxueYf2Hi6UojkpNmZ6nlPyjMOThQBQoPYOPbMG07BZytTurxjZZ1JykRBCSGGcU0q+dfXgU0mxobDL1jfezoXXDvy1ksezMjFTo1nbDsAS4bj8HwKqvUPrv6e6QJ89+u+fdr083LVbZ+ho6dHNj92UeZUjR44cOQ527dLHka7vq9GXR8WOlLizpMjWN1nRFBXb1Wj7xTOd58+cPmOccwMAje3s2bPeiV9tA6q9Q7v/+Xj506M/H4k8MiW26MSt0mNh5r7KkSNHjhy9Y8zapvq+GROt7oocpUW2LvHmiujExdZU32vvtr36P+1vtrv27dvnnQBAo6tLh46qdN9w127917/aPLxyM+0IIYQUJiOaVc+DZvKxPpnzal2S79BT1l3Xu3/S0fbSW+3/4f+bAwBLRZ06tN3vd+g5f3wJIYTMP1mrWZ34Uiry+YxYVfLSYsYrzSUXE2JT7NQLR9r2/qH9jfb2/54JACwFukN7iw4FnRi90A49dJQOTQghC4rZGlCsVJ1fsEWLu7Bd6Q2LlvId2to43vXMe+273mr7Nzo0gCUm36GDutkdunDmhjmvvsQpIYTcpvEWj8snK5pUR0tGrDInBdfDkKTYMHL4W53tL7zdrjt0e9s+EwBYGnSHrm1pDjo0IYSEIU1ZsdKMRoeyQw9YX+9q/97b7b/Zt28fHRrAUuLNh3bXuFvMuRzyAnM5CCGklkSWqf0z59bynPVZ1bE2I5rrO5ejbMw4dMe3P2j7wf59v/X+yWlra/NOAKDR9fb2+tU2IOZDE0JIPVL4v3ZmLkd+PvTK/MWQxJsP/X7brj+0+x0aAJaM/LocizsOPZ8O7f874e4oW/ISIYQQN2Y5jpNfmhYl63KE4i9nQtxVsC4HACwp+Q4d1EI6dEzZl//Y9S9XxSP5nQJi4s/00Yu+UpCtcbHVHM1171i0v0CFI/dXP3J/9SP3Vz9yf/Xjrb6/+Ci2qu6/TYhNMjTrQ+czJTZf7/nZgfafsz40gKXn7NmzfrUNqPYO7e31fb7L7PU9NLNvbWH0lYK8ONT1ojma697Rv6fqkfurH7m/+pH7qx+5v/rxVt9vjkOdu7zz0a5/VH3PxUv2KSye71GvePsUftobudB7/GzvGQBYYvxqG1DtHdqR08o8Vjis7E+U3e9Husn/SAgh5IZJ9yl5VI28Pi4eTIk7SypsXdOUFSti1j1q9HdKXs3ZCaeWRVQBYAmqvUNLmdY1OisTOTmZk7Gqic6k5DohhNzOyf9tjCp5Wcl3rx78ZlJsmFNkFyXlnl3RBVqK1dcOPazkSf3X3pHpjLsAlP+PAAA0stq2VslbSIcOJO0GAFDI/9uo5IRyuqdO75kSm/MVdlFTrkNnRLPu9E73s0qe876uYy/0Xx0ACKegf9wWrUMDACoyT5jYg2roP+PvtOYr7HxSdnfu2fjN+MYp2TfRiy3WjkfuVcOvKPuS/0UBAC46NADUkzc1wpHTORlTqa6RyIO2WDP/nVZuVocuE2u52aFQ/J1KRZQc9r4tAMBDhwaAMDBPmCh5TvXtjlnbdI3WLTYXKT88XGPm16fzn5gVTWZFjv5fKrtf93vvWzIZGgA8dGgACAslB1X67ZEDTySt9YXzkm9O5tehlbVcHzNipS3WThz4qkq/r5xrjkz6XxEA4KJDA0DdZdy4e1fJfnnx9bjY5k7nqK1GFz0XqFNuvkfhDeb+kgFvW7RExV+qi3uV7HMLNA+FA0AROjQA1J3p0O6icdPuOnfHJ997Ir/vd/DpHPPp0IVx77eWz3yQWRM6ITaNHv6Okh3eTGjvcXUmcgBAHh0aAOqpcDUl206ZWdH2ZTXw6xFrx7S1rqT73qgNzyM3mtHhrgm9ZkJ8JTfwhpKf5OSk/+UAAAXo0AAQKhmzVrQ8lel/acLanrJasmKlN0fZFNxb3qGbbLE2at2t+vco2asLtO70DD8DwFx0aACoM3umo3o7aTsyqeSYkidT3d/TddYWLXOa7k1dr2M2Tbqvx8XWRPce5ZxSckQXaGZCA0BZdGgAqLPCDp2f2qHksJIHYh/+fUy06hptRqNLK+/NiD/CbeZA60+ZElumPnxayWO6xGdZiwMAKqNDA0Bd+Lt8z+Ffz8pJJQfU5DvxY/8QE+VHo29W3DnQzXGxNfrh8/oTlT3oyKTDCDQAVEaHBoC6qNihbTvlnbi7rgwo56CZ1CG2p8Sd7kodRWtuLDhN+j2nrXVm0kj388ru1AU6KxPuFwAAVESHBoAQyc/lcE/S7mj0sJInnQsvjVg7UpHP5/Z/RlmfKanCNT5rGFmW2X9HSqwbFw/YF36h5EdKmgLtjUDbzuwkEwBACTo0AISdecTQOakGfx3teCwutk1HNthijbsJi9+GA3ZoM/tZitXTYn1MtMaPPK6uvKpkT05G3YcIZ9GhAaASOjQAhFR+THpmpY6LyjkhL74+cuCJaGSbrtFzyrHJDfu0twJ03Nqi30deekU5x5X8RMkJbxUOs9WL+7H2bINmYjQAlKJDA0CIFG65UkgXWt1x3V0M+1Vqv+rbPSp2xETrlNicFBtS4k5di6VozohVGbHSHaI2uw9mxUr3SrN+Vd+j79T3R6279e+qvh+Z95HnlIz6n+HyOrRbo8324y46NACUokMDQGPwdjHMyZiSQ8ruU9NH1dB/JT5+8erBp8bFg3GxLSE2JcWfpiOf06VZt+dsZJWMtKTEuqTYqF8dj+zQd05+vEcNv6mmO5XsU3JQF+isPe0NPAMA5o8ODQANw9sy0IwTmzI9qWxdgj9W8oAa+U3u/K5M73fTp3Ze73o83vE3E+K+qY6Hkp2P2yd3Znu+q/p263vMnfK0cgbcDQjNtA3/3ejQABAQHRoAGpFZ+S4rE+7G4MPuhOZ+d2LGGeX0mmJtnzZ12e4xV8x1/aq+R985kXV38NZv4c0boUMDQA3o0ADQwLyx5IWgQwNADejQANAYKj1u6MoUPALom3e9LvO7AIDq6NAAsARkHCebL81e28537qrlW6NDA0BgdGgAaDj+PuElXRkAsGjo0ADQcPwOXYOFz58GAGh0aABoUJWatJmbYTuVduounbnh2Bkd/wcAwPzQoQGgQd2cDl3uCgDgBujQAHAbojcDwILQoQHgNjTboSuPWAMAKqJDA0DDY58UAFhkdGgAaHh0aABYZHRoAGgwNGYAqDs6NAA0GDo0ANQdHRoAAAAIhg4NAAAABEOHBgAAAIKhQwMAAADB0KEBAACAYOjQAAAAQDB0aAAAACAYOjQAAAAQDB0aAAAACIYODQAAAARDhwYAAACCoUMDAAAAwdChAQAAgGDo0AAAAEAwdGgAAAAgGDo0AAAAEAwdGgAAAAiGDg0AAAAEIeX/AyqC9XK2scMaAAAAAElFTkSuQmCC",
                "iVBORw0KGgoAAAANSUhEUgAAAEEAAAAZCAIAAADhSNSIAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAASdEVYdFNvZnR3YXJlAEdyZWVuc2hvdF5VCAUAAANQSURBVFhH7ZVpV9pAFIb7//9ArXVpba3aXUWQigtu1VYtrj2iCIGwB2UJS0gCfdKhAbFU00/icc57OJc7c2fue7c8qff/elgcqhWtj6BpLbdZbQ4r/bZafndxmH4a7hc8crgf6MnBNRjpwsyzSJexU8wMhGcHuy9BI26e/d/7e3KolQ2g1xum0RDy0eZll/G/ET4qbs2lOjUbM0lDb3x5FbM17mGpVjF2FjLIlZLuHYvaW3dHTw5iO7iXT1yU7dOOkM9qgRWlU0Oky0X9cCNna766UnXNnH9hub4xnSRR9tbd4ZgDUZSDar1mFnLapssK87Y7rVVNEd19v1JVjeWpOAlsNJrkUElUbVtwuJ7LK5rta/S0JJ+pQm42m8tv4wiu55HT3SvyQ2Y4T6V992ZKV3VhFfAryMJkbylLpJxx4JbLVO18v+AZlQihVjO9YzGUiZAqnZQIJw/v+rJo3CNSQdHwYG5Iss2B742sa+bSpIzsGY1Ccv1zUmzZHCLHxUy0Ql0tTsrQ4KG5YckwWkWYDJcbZnPlnXUyFlRPti6dcaCgaQzRhRRGLlkj8Oi9L6Por9JaPNQ+fLOWAPSURE2UE2zVvM60EFuCA+5qFQNiPAEuDgqhgwK7qUgZK9eQRJ6ln6XTnTxuqAWdk8447K8qDbPV4gJEWmydBfKWE1NWeAT+ygHs+DJq3ioMbg7tW/4JCA4kE4F82k/gMbsHazkyQBD53ZxNET7fuFwtG3B2xoEBUi7o9l8bXEREK0X94rDtUy8OYhZ9m08zoxZet2eU4OAZkdCLouoExcM8iAVL9AD5QeZy4ZszDlQ5PfBjWSGK1ACxEdOQDlHiVd+ETK+vfUqIw5RW5wjqhHyuUhLZ+LV2FxzwL5eohY+LVsUOhLc9af8H60L0mNBLdBR/CRYp2vVZQ9kZB7DlTnMXxUDUqVFadvVjgvkoqogOK+bqriGrxM8DeZ5MXjcXYIziMSHoVAoOCHjJPOB+clvMaYu/nQY0BnqIIeOG/am5hQNh9o230y1ANnhscUIWX1YaeulPGzAW/e8T/CITOebPTXMBakMcs2EbAgRsiUvnGeaYXXvo2RV8buHQF3jQHMh136Anh75aLb87OfTveuRwH1a9/gufpsTTUbXJDQAAAABJRU5ErkJggg=="
        });
        Map<String, Object> questionFive = new HashMap<>();
        questionFive.put("question", "What is the 'Toast' class used for?");
        questionFive.put("answer", "It creates a small display that shows the user a message without disrupting their app usage.");
        questionFive.put("options", new String[]{
                "To get a user to celebrate the app.",
                "It displays a piece of toast on-screen.",
                "Shows a message that the user must acknowledge before they can continue app usage.",
                "It creates a small display that shows the user a message without disrupting their app usage."
        });

        return Arrays.asList(
                new Object[][]{
                        {1, questionOne, 3},
                        {2, questionTwo, 3},
                        {3, questionThree, 3},
                        {4, questionFour, 2},
                        {5, questionFive, 3}
                }
        );
    }

    @Parameter(0)
    public int mQuestionNumber;

    @Parameter(1)
    public Map<String, Object> mQuestion;

    @Parameter(2)
    public int mCorrectResponseNumber;

    @Test
    public void testQuestionServiceBindsWithNoErrors()  throws TimeoutException  {
        /* Arrange */
        Intent serviceIntent = new Intent(getApplicationContext(), QuestionService.class);
        IBinder binder = mServiceRule.bindService((serviceIntent));
        QuestionService questionService = ((QuestionService.QuestionBinder) binder).getService();

        /* Act */
        questionService.createQuestionSet();
    }

    @Test
    public void testGetQuestionReturnsQuestions() throws TimeoutException, JSONException {
        /* Arrange */
        Intent serviceIntent = new Intent(getApplicationContext(), QuestionService.class);
        IBinder binder = mServiceRule.bindService((serviceIntent));
        QuestionService questionService = ((QuestionService.QuestionBinder) binder).getService();

        /* Act */
        questionService.createQuestionSet();
        JSONObject response = questionService.getQuestion(1);
        JSONArray options = response.getJSONArray("options");
        String option1 = options.getString(0);

        /* Assert */
        assertThat(response, isA(JSONObject.class));
        assertThat(options, isA(JSONArray.class));
        containsString(option1);
    }

    @Test
    public void testMarkQuestionWhenAnswerIsIncorrect()
            throws TimeoutException, ClassNotFoundException, IllegalAccessException,
            NoSuchFieldException, JSONException {
        /* Arrange */
        Intent serviceIntent = new Intent(getApplicationContext(), QuestionService.class);
        IBinder binder = mServiceRule.bindService((serviceIntent));
        QuestionService questionService = ((QuestionService.QuestionBinder) binder).getService();
        //Mocks
        JSONArray mockQuestionSet = mock(JSONArray.class);

        Class<?> questionServiceClass = Class.forName("com.not.androidace.service.QuestionService");
        Field field = questionServiceClass.getDeclaredField("questionSet");
        field.setAccessible(true);
        field.set(questionService, mockQuestionSet);

        when(mockQuestionSet.getJSONObject(mQuestionNumber-1)).thenReturn(new JSONObject(mQuestion));

        /* Act */
        questionService.markQuestion(mQuestionNumber, 1);

        /* Assert */
        assertEquals(0, questionService.getNumberOfCorrectResponses());
    }

    @Test
    public void testMarkQuestionWhenAnswerIsCorrect()
            throws TimeoutException, ClassNotFoundException, IllegalAccessException,
            NoSuchFieldException, JSONException {
        /* Arrange */
        Intent serviceIntent = new Intent(getApplicationContext(), QuestionService.class);
        IBinder binder = mServiceRule.bindService((serviceIntent));
        QuestionService questionService = ((QuestionService.QuestionBinder) binder).getService();
        //Mocks
        JSONArray mockQuestionSet = mock(JSONArray.class);

        Class<?> questionServiceClass = Class.forName("com.not.androidace.service.QuestionService");
        Field field = questionServiceClass.getDeclaredField("questionSet");
        field.setAccessible(true);
        field.set(questionService, mockQuestionSet);

        when(mockQuestionSet.getJSONObject(mQuestionNumber-1)).thenReturn(new JSONObject(mQuestion));

        /* Act */
        questionService.markQuestion(mQuestionNumber, mCorrectResponseNumber);

        /* Assert */
        assertEquals(1, questionService.getNumberOfCorrectResponses());
    }
}
