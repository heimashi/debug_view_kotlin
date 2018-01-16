#!/usr/bin/env python
#coding:utf-8
# author ShiWang
import sys, os

PACKAGE = "com.sw.example"
DEFAULT_MODULE = "example"
APK_FILE_PATH = "example/build/outputs/apk/debug/example-debug.apk"


def command(cmd):
    #print cmd
    os.system(cmd)


# ----------- 帮助说明 -----------
def help():
    print "帮助说明文档:"
    print "./tools.py t                     打印栈顶Activity"
    print "./tools.py d                     进入等待调试模式"
    print "./tools.py c                     清理app缓存"
    print "./tools.py i                     安装app"
    print "./tools.py task                  打印task栈"
    print "./tools.py details               打开app设置页面"
    print "./tools.py clean_git             清理git仓库"
    print "./tools.py profile               打印编译时间profile"
    print "./tools.py mem                   打印进程内存信息"
    print "./tools.py adj                   打印进程相关信息（进程id、oom_adj值）"
    print "./tools.py window {lines}        查看窗口层级,参数为显示的行数，不输入则为单行"
    print "./tools.py depend {module name}  打印module依赖关系,参数为module名，不输入则为主模块"
    print "./tools.py refresh_dep           更新gradle依赖的缓存，重新下载项目依赖"
    print "./tools.py search {content}      全局搜索内容"




# ----------- 工具类方法实现 -----------
# 打印栈顶Activity
def top():
    command("adb shell dumpsys activity top | grep  --color=always ACTIVITY")
    # adb shell dumpsys window windows | grep -E 'mCurrentFocus|mFocusedApp' --color=always

# 进入等待调试模式
def debug():
    command("adb shell am set-debug-app -w %s" % PACKAGE)

# 清理app缓存
def clear():
    command("adb shell pm clear %s" % PACKAGE)

# 安装app
def install():
    command("adb install -t -r %s" % APK_FILE_PATH)

# 打开app设置页面
def details():
    command("adb shell am start -a android.settings.APPLICATION_DETAILS_SETTINGS -d package:%s" % PACKAGE)

# 打印Task栈
def task():
    command("adb shell dumpsys activity activities | grep --color=always ActivityRecord | grep  --color=always Hist")

# 清理git仓库
def clean_git():
    command("git reset HEAD . && git clean -fd && git checkout -- . && git status")

# 打印编译时间profile
def profile():
    command("./gradlew assembleDebug --offline --profile")

# 打印进程内存信息
def mem():
    command("adb shell dumpsys meminfo %s" % PACKAGE)

# 打印进程相关信息（进程id、oom_adj值）
def adj():
    command_str = "adb shell ps | grep %s | grep -v : | awk '{print $2}'" % PACKAGE
    p = os.popen(command_str)
    pid = p.readline().strip()
    print "process id:%s" % pid
    command("adb root")
    print "process oom_adj:"
    command("adb shell cat /proc/%s/oom_adj" % pid)

# 查看窗口层级
def window(*args):
    if len(args)==0:
        arg = 0
    else:
        arg = args[0][0]
    command("adb shell dumpsys window windows | grep 'Window #' --color -A %s" % arg)

# 打印module依赖关系
def depend(*args):
    if len(args)==0:
        arg = DEFAULT_MODULE
    else:
        arg = args[0][0]
    command("./gradlew -q %s:dependencies" % arg)

# 更新gradle依赖的缓存，重新下载项目依赖
def refresh_dep():
    command("./gradlew build --refresh-dependencies")

# 全局搜索内容
def search(*args):
    if len(args)==0:
        return
    else:
        arg = args[0][0]
    command("grep -E %s --exclude-dir={.git,lib,.gradle,.idea,build,captures} --exclude={*.jar}  . -R --color=always -n" % arg)








# ----------- 方法缩写 -----------
def t():
    top()

def d():
    debug()

def c():
    clear()

def i():
    install()




def main():
    args = None
    if len(sys.argv) > 2:
        action = sys.argv[1]
        args = sys.argv[2:]
    elif len(sys.argv) > 1:
        action = sys.argv[1]
    else:
        action = "help"
    if args is None:
        call = "%s()" % action
    else:
        call = "%s(%s)" % (action, args)
    exec(call)


if __name__ == "__main__":
    main()