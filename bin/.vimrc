" http://nvie.com/posts/how-i-boosted-my-vim/
" http://stevelosh.com/blog/2010/09/coming-home-to-vim/
" http://vim.runpaint.org/toc/
" http://vim.wikia.com/wiki/In_line_copy_and_paste_to_system_clipboard
" http://vimcasts.org/episodes/archive
" http://vimdoc.sourceforge.net/htmldoc/map.html#:map-modes
" http://vimdoc.sourceforge.net/htmldoc/usr_41.html
" http://www.vim.org/scripts/script.php?script_id=2340

" set leader, nocompat, color, and syntax
let mapleader=","
set nocompatible
set modelines=0 " disable due to a potential security hole.
" filetype off
" call pathogen#runtime_append_all_bundles()
filetype plugin indent on
set t_Co=256
colorscheme molokai
syntax on

" set basic features 
" set clipboard+=unnamed
set autoread
set encoding=utf-8
set pastetoggle=<F2>
set title showmode showcmd ruler
set laststatus=2 " always (default: only if there are 2+ windows)
set visualbell noerrorbells hidden
set nocursorcolumn nocursorline
set mouse=a " a for gui (default: '')
set scrolloff=3
set ttyfast
set guioptions-=T
set guifont=Menlo:h12

" set tabbing and wrapping
set tabstop=4 softtabstop=4 shiftwidth=4 expandtab smarttab
set autoindent copyindent shiftround nosmartindent
set backspace=indent,eol,start
set textwidth=79
set formatoptions=qrn1 " for gq http://vimdoc.sourceforge.net/htmldoc/change.html#fo-table
set listchars=tab:▸\ ,eol:¬ " tab:▸\ ,trail:.,extends:#,nbsp:.
set wrap showbreak=…
nnoremap <leader>r :set invwrap wrap?<CR>
nnoremap <leader>l :set list!<CR>
nnoremap <leader>L :%s=\s\+$==<CR>

" search w/ very nomagic '\V'
set nowrapscan ignorecase smartcase incsearch hlsearch gdefault
set showmatch
nnoremap <silent> <space> :nohlsearch<bar>:echo<CR>
noremap / /\V
noremap ? ?\V

" move around
nnoremap j gj
nnoremap k gk
nnoremap ^ g^
nnoremap $ g$

" use the leader
vnoremap <leader>q gq
nnoremap <leader>q gqap
nnoremap <leader>v V`]
nnoremap <silent> <leader>W :call <SID>preserve("%s/\\s\\+$//e")<CR>
nnoremap <leader>ev :tabedit $MYVIMRC<CR>
nnoremap <silent> <leader>sv :source $MYVIMRC<CR>:exe ":echo '.vimrc reloaded.'"<CR>

" escape quicker
inoremap jj <Esc>
inoremap kk <Esc>
inoremap <F1> <Esc>
nnoremap <F1> <Esc>
vnoremap <F1> <Esc>
inoremap ;; <Esc>:
nnoremap ;; :
nnoremap ; :

" work w/ split windows
nnoremap <C-n> <C-w>v<C-w>l
nnoremap <C-h> <C-w>h
nnoremap <C-j> <C-w>j
nnoremap <C-k> <C-w>k
nnoremap <C-n> <C-w>v<C-w>l
inoremap <C-h> <C-w>h
inoremap <C-j> <C-w>j
inoremap <C-k> <C-w>k
inoremap <C-l> <C-w>l
inoremap <C-l> <C-w>l

" work with buffers
nnoremap <M-left> :bp<CR>
nnoremap <M-right> :bn<CR>
inoremap <M-left> <Esc>:bp<CR>
inoremap <M-right> <Esc>:bn<CR>

" sudo vim
cnoremap w!! w !sudo tee % >/dev/null

" break (shift-K), opposite of join (shift-J)
noremap K i<CR><Esc>

" yank and paste
if executable('xclip')
    vnoremap <C-c> y:call <SID>save_clip('xclip -i -selection clipboard')<CR>
    vnoremap y y:call <SID>save_clip('xclip -i -selection clipboard')<CR>
    vnoremap d d:call <SID>save_clip('xclip -i -selection clipboard')<CR>
    nnoremap yy yy:call <SID>save_clip('xclip -i -selection clipboard')<CR>
    nnoremap dd dd:call <SID>save_clip('xclip -i -selection clipboard')<CR>
    nnoremap Y y$:call <SID>save_clip('xclip -i -selection clipboard')<CR>
    nnoremap D d$:call <SID>save_clip('xclip -i -selection clipboard')<CR>
    nnoremap p :call <SID>load_clip('xclip -o -selection clipboard')<CR>p
    nnoremap P :call <SID>load_clip('xclip -o -selection clipboard')<CR>P
endif
if executable('pbcopy')
    vnoremap <C-c> y:call <SID>save_clip('pbcopy')<CR>
    vnoremap y y:call <SID>save_clip('pbcopy')<CR>
    vnoremap d d:call <SID>save_clip('pbcopy')<CR>
    nnoremap yy yy:call <SID>save_clip('pbcopy')<CR>
    nnoremap dd dd:call <SID>save_clip('pbcopy')<CR>
    nnoremap Y y$:call <SID>save_clip('pbcopy')<CR>
    nnoremap D d$:call <SID>save_clip('pbcopy')<CR>
endif
if executable('pbpaste')
    nnoremap p :call <SID>load_clip('pbpaste')<CR>p
    nnoremap P :call <SID>load_clip('pbpaste')<CR>P
endif

" correct spells by commands: ]s, [s, z=, 1z=, and u (undo)
nnoremap <silent> <leader>s :setlocal spell! spelllang=en_us<CR>

" emulate TextMate's indentations http://vimcasts.org/episodes/indentation-commands/
nnoremap <D-[> <<
nnoremap <D-]> >>
vnoremap <D-[> <gv
vnoremap <D-]> >gv

" emulate Eclipse's navigations
nnoremap <C-q> `.
nnoremap <M-D-left> <C-O>
nnoremap <M-D-right> <Tab>
inoremap <C-q> <Esc>`.
inoremap <M-D-left> <Esc><C-O>
inoremap <M-D-right> <Esc><Tab>

" emulate FireFox's navigations http://vimcasts.org/episodes/working-with-tabs/
nnoremap <D-S-]> gt
nnoremap <D-S-[> gT
nnoremap <D-1> 1gt
nnoremap <D-2> 2gt
nnoremap <D-3> 3gt
nnoremap <D-4> 4gt
nnoremap <D-5> 5gt
nnoremap <D-6> 6gt
nnoremap <D-7> 7gt
nnoremap <D-8> 8gt
nnoremap <D-9> 9gt
nnoremap <D-0> :tablast<CR>
inoremap <D-S-]> <Esc>gt
inoremap <D-S-[> <Esc>gT
inoremap <D-1> <Esc>1gt
inoremap <D-2> <Esc>2gt
inoremap <D-3> <Esc>3gt
inoremap <D-4> <Esc>4gt
inoremap <D-5> <Esc>5gt
inoremap <D-6> <Esc>6gt
inoremap <D-7> <Esc>7gt
inoremap <D-8> <Esc>8gt
inoremap <D-9> <Esc>9gt
inoremap <D-0> <Esc>:tablast<CR>

" miscellaneous
let g:HammerTemplate = 'typographic-light'
let g:HammerTemplate = 'sphinx'
let g:HammerTemplate = 'cloudapp'
let g:HammerTemplate = 'default_inline'
let g:HammerDirectory = '.'
map <leader>p :Hammer<CR>
set nobackup nowritebackup noswapfile " they are 70's ways
set history=200 undolevels=1000
set number

if exists("&colorcolumn")
    set colorcolumn=85
endif

" change lcd to the buffer, see also autochdir
autocmd BufEnter * lcd %:p:h

" jump to the last edited line based on .viminfo
autocmd BufReadPost * if line("'\"") > 0 && line("'\"") <= line("$") | exe "normal g'\"" | endif

function! <SID>load_clip(command)
    let r = system(a:command)
    call setreg('"', r)
endfunction

function! <SID>save_clip(command)
    let r = getreg('"')
    call system(a:command, r)
    echo system('sed -n $= | tr -d \\n', r) 'lines clipped'
endfunction

function! <SID>preserve(command)
    let _s = @/ " backup search history
    let l = line(".") " backup line #
    let c = col(".") " backup column #
    execute a:command
    let @/ = _s " restore search history
    call cursor(l, c) " restore cursor
endfunction
