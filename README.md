# zno

A small ZNO quiz manager for my brother.

Installation path is `C:\Program Files\zno`

## Emphasis' Quiz

To display help run:
```powershell
.\zno.exe help
```

Usage:
```powershell
.\zno.exe <path\to\data.txt> <limit:int> <offset:int> <randomize:[true|false]>
```

---

- To run a full test, just double-click `zno.exe`
- To run first questions [1-10] in random order:
  ```powershell
  .\zno.exe .\app\data.txt 10 0 true
  ```
