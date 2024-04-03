package jp.co.nexus;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	/** 
	 * allUserPageNationListメソッド
	 * 削除されていないデータ1件表示できること。
	 * 期待値
	 * 取得できるデータのseq_idが"100"
	 **/
	@Test
	@DatabaseSetup("/testdata/UserRepositoryTest/case01/init-data") // テスト実行前に初期データを投入
	@ExpectedDatabase(value = "/testdata/UserRepositoryTest/case01/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後のデータ検証（初期データのままであること）
	public void userTest001() {
		int pageSize = 20;
		int offset = 0;

		List<Map<String, Object>> allUserPageNationList = target.allUserInfoPageNation(pageSize, offset);
		Object result = allUserPageNationList.get(0).get("seq_id");
		assertEquals(100, result);
	}

	/** 
	 * allUserPageNationListメソッド
	 * 削除されていないデータ2件表示できること。
	 * 期待値
	 * 取得したデータの2件目がseq_idが"102"
	 **/
	@Test
	@DatabaseSetup("/testdata/UserRepositoryTest/case02/init-data") // テスト実行前に初期データを投入
	@ExpectedDatabase(value = "/testdata/UserRepositoryTest/case02/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後のデータ検証（初期データのままであること）
	public void userTest002() {
		int pageSize = 20;
		int offset = 0;

		List<Map<String, Object>> allUserPageNationList = target.allUserInfoPageNation(pageSize, offset);
		Object result = allUserPageNationList.get(1).get("seq_id");
		assertEquals(102, result);
	}

	/** 
	 * allUserPageNationListメソッド
	 * 削除されていないデータ2件表示できること。
	 * 期待値
	 * 取得したデータの2件目がseq_idが"102"
	 **/
	@Test
	@DatabaseSetup("/testdata/UserRepositoryTest/case03/init-data") // テスト実行前に初期データを投入
	@ExpectedDatabase(value = "/testdata/UserRepositoryTest/case03/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後のデータ検証（初期データのままであること）
	public void userTest003() {
		int pageSize = 20;
		int offset = 0;

		List<Map<String, Object>> allUserPageNationList = target.allUserInfoPageNation(pageSize, offset);
		assertTrue(allUserPageNationList.isEmpty());
	}

	/** 
	 * allUserPageNationListメソッド
	 * 21件目が表示できること。
	 * 期待値
	 * 取得したデータの1件目のseq_idが"120"。
	 **/
	@Test
	@DatabaseSetup("/testdata/UserRepositoryTest/case04/init-data") // テスト実行前に初期データを投入
	@ExpectedDatabase(value = "/testdata/UserRepositoryTest/case04/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後のデータ検証（初期データのままであること）
	public void userTest004() {
		int pageSize = 20;
		int offset = 20;

		List<Map<String, Object>> allUserPageNationList = target.allUserInfoPageNation(pageSize, offset);
		Object result = allUserPageNationList.get(0).get("seq_id");
		assertEquals(120, result);
	}

	/** 
		* userDeleteメソッド
		* 指定した1件のユーザが削除されること。
		* 期待値
		* 削除件数が1件であること
		**/

	@Test
	@DatabaseSetup("/testdata/UserRepositoryTest/case01/init-delete-data") // テスト実行前に初期データを投入
	@ExpectedDatabase(value = "/testdata/UserRepositoryTest/case01/after-delete-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後のデータ検証（初期データのままであること）
	public void userDeleteTest001() {
		// 削除対象用リスト
		List<Map<String, Object>> registLockDataList = new ArrayList<>();

		Map<String, Object> data = new HashMap<>();
		data.put("seq_id", 100);
		data.put("user_id", 200);
		registLockDataList.add(data);

		List<Map<String, Object>> resultList = target.userDelete(registLockDataList);
		assertEquals(1, resultList.size());
		assertEquals(100, resultList.get(0).get("seq_id"));
	}

	/** 
	* userDeleteメソッド
	* 指定した2件のユーザが削除されること。
	* 期待値
	* 削除件数が2件であること
	**/

	@Test
	@DatabaseSetup("/testdata/UserRepositoryTest/case02/init-delete-data") // テスト実行前に初期データを投入
	@ExpectedDatabase(value = "/testdata/UserRepositoryTest/case02/after-delete-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後のデータ検証（初期データのままであること）
	public void userDeleteTest002() {
		// 削除対象用リスト
		List<Map<String, Object>> registLockDataList = new ArrayList<>();

		Map<String, Object> data1 = new HashMap<>();
		data1.put("seq_id", 100);
		data1.put("user_id", 200);
		registLockDataList.add(data1);

		Map<String, Object> data2 = new HashMap<>();
		data2.put("seq_id", 102);
		data2.put("user_id", 202);
		registLockDataList.add(data2);

		List<Map<String, Object>> resultList = target.userDelete(registLockDataList);
		assertEquals(2, resultList.size());
	}

	/** 
	* userDeleteメソッド
	* 指定した2件のユーザが削除されること。
	* 期待値
	* 削除件数が2件であること
	**/

	@Test
	@DatabaseSetup("/testdata/UserRepositoryTest/case03/init-delete-data") // テスト実行前に初期データを投入
	@ExpectedDatabase(value = "/testdata/UserRepositoryTest/case03/after-delete-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後のデータ検証（初期データのままであること）
	public void userDeleteTest003() {
		// 削除対象用リスト
		List<Map<String, Object>> registLockDataList = new ArrayList<>();
		List<Map<String, Object>> resultList = target.userDelete(registLockDataList);
		assertTrue(resultList.isEmpty());
	}
}
