# color prompts, e.g. 32;40, or 30;46
export PS1='\[\e]2;\u@\h:\@:\w\a\e]1;$(basename $(dirname $(PWD)))/\W\a\e[32;40m\]\t:$(basename $(dirname $(PWD)))/\W>\[\e[0m\] '
export TERM='xterm-color'
export CLICOLOR=1
export LSCOLORS=ExFxCxDxBxegedabagacad
export CATALINA_OPTS='-Xmx1536m -XX:MaxPermSize=512m -Dlog.dir=/tmp/ -Xdebug -Xrunjdwp:transport=dt_socket,address=5005,suspend=n,server=y'
export GREP_OPTIONS='--color=auto'

# homes and paths
export ANDROID_HOME=/workspace/android-sdk
export GROOVY_HOME=/usr/lib/groovy
export JAVA_HOME=/Library/Java/Home
export M3_HOME=/usr/share/maven
export SWIFTMQ_HOME=/workspace/swiftmq-7.5.3
export TOMCAT_HOME=/workspace/apache-tomcat-6.0.24
export PATH=$PATH:~/bin:/opt/local/bin:/opt/local/sbin
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools

[[ -s "$HOME/.rvm/scripts/rvm" ]] && . "$HOME/.rvm/scripts/rvm" # loads `rvm` as a shell function.

# aliases for git
source .git-aliases

# misc. aliases
alias diff='colordiff'
alias gmail='openssl s_client -crlf -quiet -connect imap.gmail.com:993'
alias less='less -NMI'
alias l='ls -alv'
alias ll='ls -alv'
alias ls='ls -av'
alias m2eclipse='mvn eclipse:eclipse -DdownloadSources=true'
alias mysql='/opt/local/bin/mysql5'
alias mysqldump='/opt/local/bin/mysqldump5'
alias sdf='svn diff --diff-cmd=svn-diff'
alias vim='/Applications/MacVim.app/Contents/MacOS/Vim'
alias wget='wget --no-check-certificate'

# ssh-aliases
alias acymmer='ssh acymmer.desktop'
alias ashokth='ssh ashokth.desktop'
alias geio='ssh geio-7001.iad7'
alias hylee-x='ssh hylee.desktop'
alias hylee='ssh -X hylee.desktop'
alias ii='ssh i-interactive'
alias jameslee='ssh -X jameslee.desktop'
alias jaschen='ssh jaschen.desktop'
alias vineeth='ssh vineeth.desktop'

# directory-aliases
alias ..='cd ..'
alias ...='cd ../..'
alias ....='cd ../../..'
alias .....='cd ../../../..'
alias and='cd /workspace/gits/android-2.x'
alias apk='cd /workspace/gits/henry4j/projs./apk'
alias config='cd /workspace/gits/config'
alias dashconfig='cd /workspace/gits/dashconfig'
alias email='cd /workspace/gits/email'
alias gits='cd /workspace/gits'
alias henry='cd /workspace/gits/henry4j'
alias labs='cd /workspace/gits/henry4j/labs'
alias notes='cd /workspace/gits/notes'
alias projs='cd /workspace/gits/henry4j/projs.'
alias savanna='cd /workspace/gits/savanna'
alias vending='cd workspace/gits/henry4j-/projs./apk/com.android.vending-3.1.5'
alias workspace='cd /workspace'
alias ws='cd /workspace'

# set the number of open files to be 1024
ulimit -S -n 1024
