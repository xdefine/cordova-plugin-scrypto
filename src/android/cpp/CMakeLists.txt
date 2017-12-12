
cmake_minimum_required(VERSION 3.4.1)

add_library ( encrypt_token SHARED src/main/cpp/encrypt_token.cpp )
add_library ( md5 SHARED src/main/cpp/md5.cpp )
add_library ( sha1 SHARED src/main/cpp/sha1.cpp )
find_library( log-lib log )

target_link_libraries( encrypt_token md5 sha1 ${log-lib} )
