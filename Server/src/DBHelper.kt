import java.sql.DriverManager
import kotlin.random.Random


class DBHelper {
    val url = "jdbc:mysql://localhost:3306/bank"
    val user = "root"
    val password = ""
    val connection = DriverManager.getConnection(url, user, password)
    val statement = connection.createStatement()

    fun check_login(login: String): String?{
        var res = statement.executeQuery("SELECT login FROM users WHERE login = '$login'")
        var s: String? = null
        while(res.next()){
            s = res.getString(1)
        }
        return s
    }

    fun insert(login: String, password: String, name: String, acc_num: String){
        val randomInt = Random.nextInt(30000, 200000)
        statement.executeUpdate("INSERT INTO users (login, password, name, account_num, salary) VALUES ('$login', '$password', '$name', '$acc_num', $randomInt)")
    }

    fun check_password(login: String): String?{
        var res = statement.executeQuery("SELECT password FROM users WHERE login = '$login'")
        var s: String? = null
        while(res.next()){
            s = res.getString(1)
        }
        return s
    }

    fun get_info(login: String): String{
        var res = statement.executeQuery("SELECT name, account_num, salary FROM users WHERE login = '$login'")
        var s = ""
        var cnt = 1
        while(res.next()){
            s = res.getString(1) + " " + res.getString(2) + " " + res.getString(3)
        }
        return s
    }
}