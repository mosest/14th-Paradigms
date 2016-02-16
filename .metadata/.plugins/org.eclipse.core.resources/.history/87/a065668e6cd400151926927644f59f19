class Random {
	long m_a;
	long m_b;

	Random(long seed) {
		setSeed(seed);
	}

	Random(Random that) {
		m_a = that.m_a;
		m_b = that.m_b;
	}

	void setSeed(long seed) {
		m_b = 0xCA535ACA9535ACB2l + seed;
		m_a = 0x6CCF6660A66C35E7l + (seed << 24);
	}

	long nextFullLong() {
		m_a = 0x141F2B69l * (m_a & 0x3ffffffffl) + (m_a >> 32);
		m_b = 0xC2785A6Bl * (m_b & 0x3ffffffffl) + (m_b >> 32);
		return m_a ^ m_b;
	}

	long nextLong(long range) {
		long n = (0xffffffffffffffffl % range) + 1;
		long x;
		do {
			x = nextFullLong();
		} while((x + n) < n);
		return x % range;
	}

	int nextInt(int range) {
		return (int)nextLong((long)range);
	}

	boolean nextBoolean() {
		return (nextLong(2) == 0 ? true : false);
	}
}