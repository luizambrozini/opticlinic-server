export interface MenuItem {
  id: string;
  label: string;
  icon?: string;
  path?: string;
  items?: MenuItem[];
}

export interface MenuSection {
  id: string;
  title: string;
  icon?: string;
  items: MenuItem[];
}
