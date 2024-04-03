package jp.co.nexus;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.nexus.whc.repository.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class) // DBUnitでCSVファイルを使えるよう指定。＊CsvDataSetLoaderクラスは自作します（後述）
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class,
		TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや＠ExpectedDatabaseなどを使えるように指定
})
@ContextConfiguration(classes = UserRepository.class)
@TestPropertySource(locations = "classpath:application.properties")
@SpringBootApplication
@Transactional // @DatabaseSetupで投入するデータをテスト処理と同じトランザクション制御とする。（テスト後に投入データもロールバックできるように）

public class UserRepositoryTest {
	@Autowired
	private UserRepository target;
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	@DatabaseSetup("/testdata/UserRepositoryTest/case01/ini-data") // テスト実行前に初期データを投入
	@ExpectedDatabase(value = "/testdata/UserRepositoryTest/case01/ini-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後のデータ検証（初期データのままであること）
	public void userTest001() {
		fail();
	}

}
