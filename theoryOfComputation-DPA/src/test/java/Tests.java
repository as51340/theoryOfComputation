import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Tests {

	@Test
	void test16() {
		String expected = "q0#K|q1#XK|q1#YXK|q2#YXK|q2#XYXK|pp#XYXK|1";
		String actual = "q0#K|q1#XK|q1#YXK|q2#YXK|q2#XYXK|pp#XYXK|1";
		assertEquals(expected, actual);
	}
	
	@Test
	void test17() {
		String expected = "p0#K|p1#AAK|p1#AAAAK|p1#AAAK|p1#AAK|0";
		String actual = "p0#K|p1#AAK|p1#AAAAK|p1#AAAK|p1#AAK|0";
		assertEquals(expected, actual);
	}
	
	@Test
	void test18() {
		String expected = "p0#K|p1#AAK|p1#AK|p1#K|p2#K|1";
		String actual = "p0#K|p1#AAK|p1#AK|p1#K|p2#K|1";
		assertEquals(expected, actual);
	}
	
	@Test
	void test19() {
		String expected = "p0#K|p1#AAK|p1#AK|p1#AAAK|p1#AAK|p1#AK|p1#K|p2#K|1";
		String actual = "p0#K|p1#AAK|p1#AK|p1#AAAK|p1#AAK|p1#AK|p1#K|p2#K|1";
		assertEquals(expected, actual);
	}
	
	@Test
	void test20() {
		String expected1 = "p0#K|p1#AAK|p1#AK|p1#AAAK|p1#AAK|p1#AK|p1#K|p2#K|1";
		String expected2 = "p0#K|p1#AAK|p1#AK|p1#K|p2#K|fail|0";
		String expected3 = "p0#K|fail|0";
		String expected4 = "p0#K|p1#AAK|p1#AAAAK|p1#AAAK|p1#AAK|p1#AK|p1#K|p2#K|1";
		String expected5 = "p0#K|p1#AAK|p1#AK|p1#K|p2#K|fail|0";
		String actual1 = "p0#K|p1#AAK|p1#AK|p1#AAAK|p1#AAK|p1#AK|p1#K|p2#K|1";
		String actual2 = "p0#K|p1#AAK|p1#AK|p1#K|p2#K|fail|0";
		String actual3 = "p0#K|fail|0";
		String actual4 = "p0#K|p1#AAK|p1#AAAAK|p1#AAAK|p1#AAK|p1#AK|p1#K|p2#K|1";
		String actual5 ="p0#K|p1#AAK|p1#AK|p1#K|p2#K|fail|0";
		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
		assertEquals(expected3, actual3);
		assertEquals(expected4, actual4);
		assertEquals(expected5, actual5);
	}
	
	@Test
	void test21() {
		String expected1 = "p0#K|p0#AAAK|p0#AAK|p0#AK|p0#K|p1#K|1";
		String actual1 = "p0#K|p0#AAAK|p0#AAK|p0#AK|p0#K|p1#K|1";
		assertEquals(expected1, actual1);
	}
	

	@Test
	void test22() {
		String expected1 = "p0#K|p0#BK|p0#BBK|p0#AABK|p0#ABK|fail|0";
		String actual1 = "p0#K|p0#BK|p0#BBK|p0#AABK|p0#ABK|fail|0";
		assertEquals(expected1, actual1);
	}
	
	@Test
	void test23() {
		String expected1 = "p0#K|p1#K|p1#K|p0#AAAK|p0#AAK|p0#AK|p0#K|p1#K|1";
		String actual1 = "p0#K|p1#K|p1#K|p0#AAAK|p0#AAK|p0#AK|p0#K|p1#K|1";
		assertEquals(expected1, actual1);
	}
	
	@Test
	void test24() {
		String expected1 = "p0#K|p1#K|p1#K|p0#AAAK|p0#AAK|p0#AK|p0#K|p1#K|1";
		String expected2 = "p0#K|p0#BK|p0#AAK|p0#AK|p0#K|p1#K|1";
		String expected3 = "p0#K|p0#BK|p0#BBK|p0#AABK|p0#ABK|p0#BK|p0#AAK|p0#AK|p0#K|p1#K|p1#K|1";
		String expected4 = "p0#K|p1#K|p1#K|p0#AAAK|p0#AAAAAAK|p0#AAAAAK|p0#AAAAK|p0#AAAK|p0#AAAAAAK|p0#AAAAAK|p0#AAAAK|p0#AAAK|fail|0";
		String actual1 = "p0#K|p1#K|p1#K|p0#AAAK|p0#AAK|p0#AK|p0#K|p1#K|1";
		String actual2 = "p0#K|p0#BK|p0#AAK|p0#AK|p0#K|p1#K|1";
		String actual3 = "p0#K|p0#BK|p0#BBK|p0#AABK|p0#ABK|p0#BK|p0#AAK|p0#AK|p0#K|p1#K|p1#K|1";
		String actual4 = "p0#K|p1#K|p1#K|p0#AAAK|p0#AAAAAAK|p0#AAAAAK|p0#AAAAK|p0#AAAK|p0#AAAAAAK|p0#AAAAAK|p0#AAAAK|p0#AAAK|fail|0";
		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
		assertEquals(expected3, actual3);
		assertEquals(expected4, actual4);
	}
	
	@Test
	void test25() {
		String expected1 = "p0#K|p1#K|p1#K|p0#AAAK|p0#AAK|p0#AK|p0#K|p1#K|p0#AAAK|p0#AAK|p0#AK|p0#K|0";
		String expected2 = "p0#K|p0#BK|p0#AAK|p0#AK|p0#K|p1#K|p0#AAAK|p0#AAAAAAK|p0#AAAAAK|p0#AAAAK|p0#AAAK|p0#AAK|p0#AK|p0#K|p1#K|1";
		String expected3 = "p0#K|p0#BK|p0#BBK|p0#AABK|p0#ABK|p0#BK|p0#AAK|p0#AK|p0#K|p1#K|p1#K|p0#AAAK|p0#AAK|p0#AK|p0#K|p0#AAAK|p0#AAAAAAK|p0#AAAAAK|p0#AAAAK|0";
		String expected4 = "p0#K|p1#K|p1#K|p0#AAAK|p0#AAAAAAK|p0#AAAAAK|p0#AAAAK|p0#AAAK|p0#AAAAAAK|p0#AAAAAK|p0#AAAAK|p0#AAAK|p0#AAK|p0#AAAAAK|p0#AAAAK|p0#AAAK|p0#AAK|p0#AK|fail|0";
		String actual1 = "p0#K|p1#K|p1#K|p0#AAAK|p0#AAK|p0#AK|p0#K|p1#K|p0#AAAK|p0#AAK|p0#AK|p0#K|0";
		String actual2 = "p0#K|p0#BK|p0#AAK|p0#AK|p0#K|p1#K|p0#AAAK|p0#AAAAAAK|p0#AAAAAK|p0#AAAAK|p0#AAAK|p0#AAK|p0#AK|p0#K|p1#K|1";
		String actual3 = "p0#K|p0#BK|p0#BBK|p0#AABK|p0#ABK|p0#BK|p0#AAK|p0#AK|p0#K|p1#K|p1#K|p0#AAAK|p0#AAK|p0#AK|p0#K|p0#AAAK|p0#AAAAAAK|p0#AAAAAK|p0#AAAAK|0";
		String actual4 = "p0#K|p1#K|p1#K|p0#AAAK|p0#AAAAAAK|p0#AAAAAK|p0#AAAAK|p0#AAAK|p0#AAAAAAK|p0#AAAAAK|p0#AAAAK|p0#AAAK|p0#AAK|p0#AAAAAK|p0#AAAAK|p0#AAAK|p0#AAK|p0#AK|fail|0";
		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
		assertEquals(expected3, actual3);
		assertEquals(expected4, actual4);
	}
	
	
	@Test
	void test10() {
		String expected = "q0#K|q0#XK|q0#KXK|q0#XKXK|q0#KXKXK|q1#KXKXK|0";
		String actual = "q0#K|q0#XK|q0#KXK|q0#XKXK|q0#KXKXK|q1#KXKXK|0";
		assertEquals(expected, actual);
	}
	
	@Test
	void test11() {
		String expected = "q0#K|q0#XK|q1#XXK|q1#KXXK|q1#KXXK|q2#KXXK|1";
		String actual = "q0#K|q0#XK|q1#XXK|q1#KXXK|q1#KXXK|q2#KXXK|1";
		assertEquals(expected, actual);
	}
	
	@Test
	void test12() {
		String expected = "q0#K|q0#XK|q1#XXK|q1#KXXK|q1#KXXK|q2#KXXK|1";
		String actual = "q0#K|q0#XK|q1#XXK|q1#KXXK|q1#KXXK|q2#KXXK|1";
		assertEquals(expected, actual);
	}
	
	@Test
	void test13() {
		String expected = "q0#K|q1#XK|q1#YXK|q2#YXK|q2#XYXK|0";
		String actual = "q0#K|q1#XK|q1#YXK|q2#YXK|q2#XYXK|0";
		assertEquals(expected, actual);
	}
	
	@Test
	void test14() {
		String expected = "q0#K|q1#XK|q1#YXK|q2#YXK|pp#XYXK|1";
		String actual = "q0#K|q1#XK|q1#YXK|q2#YXK|pp#XYXK|1";
		assertEquals(expected, actual);
	}
	
	@Test
	void test15() {
		String expected = "q0#K|q1#XK|q1#YXK|q2#YXK|q2#XYXK|pp#XYXK|1";
		String actual = "q0#K|q1#XK|q1#YXK|q2#YXK|q2#XYXK|pp#XYXK|1";
		assertEquals(expected, actual);
	}
	

}
