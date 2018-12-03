#!/usr/bin/env bash

set -e

#brew install mysql
#brew services start mysql

mysql_user=spring
mysql_db=witchcraft
mysql_db_test=${mysql_db}_test

echo 'Remove existing databases'
mysql -uroot -e "DROP DATABASE IF EXISTS $mysql_db;"
mysql -uroot -e "DROP DATABASE IF EXISTS $mysql_db_test;"
mysql -uroot -e "DROP USER '$mysql_user'@'localhost';"

echo "Create user '$mysql_user'"
mysql -uroot -e "CREATE USER '$mysql_user'@'localhost' IDENTIFIED BY ''";

echo "Create database $mysql_db and grant access to $mysql_user"
mysql -uroot -e "CREATE DATABASE $mysql_db";
mysql -uroot -e "GRANT ALL PRIVILEGES ON *.* TO '$mysql_user'@'localhost'";

echo "Create database $mysql_db_test and grant access to $mysql_user"
mysql -uroot -e "CREATE DATABASE $mysql_db_test";
mysql -uroot -e "GRANT ALL PRIVILEGES ON $mysql_db_test.* TO '$mysql_user'@'localhost'";
